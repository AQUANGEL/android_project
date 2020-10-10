package com.vladlozkin.libgdk_protector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.vladlozkin.libgdk_protector.BalloonImpl.BalloonExtinguisher;
import com.vladlozkin.libgdk_protector.Levels.ExtinguisherTeacher;
import com.vladlozkin.libgdk_protector.Levels.ILevel;
import com.vladlozkin.libgdk_protector.Levels.LevelOne;
import com.vladlozkin.libgdk_protector.Levels.LevelThree;
import com.vladlozkin.libgdk_protector.Levels.LevelTwo;
import com.vladlozkin.libgdk_protector.Levels.PopTutorial;

import java.util.concurrent.CopyOnWriteArrayList;

public class OtefProtectorGame {

    ILevel m_Level;

    CopyOnWriteArrayList m_EnemysToDraw;
    CopyOnWriteArrayList m_EnemysWaitingList;
    CopyOnWriteArrayList itemsToRemove;
    CopyOnWriteArrayList itemsToRemoveAfterImpact;

    IEnemy enemyInWaitingList;
    IEnemy enemyInToDrawList;
    IEnemy enemy;
    IEnemy enemyFoCheckingOverlap;

    private int m_score = 0;
    private int m_NumberOfEnemies;
    private int RENDER_LOOPS_AFTER_LAST_ENEMY = 20;
    private int renderCounter = 0;
    private boolean m_IntroLevel = false;
    private int m_CurrentLevel;

    Label scoreText;
    Label.LabelStyle scoreTextStyle;
    BitmapFont scoreFont;

    private int m_MsForEnemyDispatch = 1500;
    private int m_MsInWaitingList = 1500;

    private boolean m_showLoginScreen = true;

    Rectangle screenRectangel;
    SpriteBatch m_SpriteBatch;

    IActionResolver m_ActionResolver;

    private Preferences PREFS;
    Sound extinguishSound = Gdx.audio.newSound(Gdx.files.internal("extinguishe.wav"));

    private int life_points;

    private static final int FIRST_LEVEL = 2;
    private static final int LAST_LEVEL = 5;
    private static final int INTRO_LEVEL = 0;
    private static final int INITIAL_LIFE_POINTS = 3;

    public OtefProtectorGame(IActionResolver actionResolver,  SpriteBatch spriteBatch )
    {
        m_ActionResolver = actionResolver;
        m_SpriteBatch = spriteBatch;

        m_EnemysToDraw = new CopyOnWriteArrayList<IEnemy>();
        m_EnemysWaitingList = new CopyOnWriteArrayList<IEnemy>();
        itemsToRemove = new CopyOnWriteArrayList<IEnemy>();
        itemsToRemoveAfterImpact = new CopyOnWriteArrayList<IEnemy>();
        screenRectangel = new Rectangle(0, Gdx.graphics.getHeight()/12 ,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        scoreFont = new BitmapFont();
        scoreFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        scoreTextStyle = new Label.LabelStyle();
        scoreTextStyle.font = scoreFont;
        PREFS = Gdx.app.getPreferences("Game_Prefs");

        life_points = INITIAL_LIFE_POINTS;
        m_CurrentLevel = PREFS.getBoolean("show_intro", true) ? INTRO_LEVEL : FIRST_LEVEL ;

    }

    public void InitGame()
    {
        scoreText = new Label("Score: 0",scoreTextStyle);
        scoreText.setBounds(32, 32,200,100);
        scoreText.setFontScale(4f,4f);

        setLevel(m_CurrentLevel);
        startDispatchThreads();
    }

    public void RenderGame()
    {
        if (m_showLoginScreen)
        {
            m_ActionResolver.ShowMainMenu();
            m_showLoginScreen = false;
        }

        m_Level.RenderBackground();
        renderEnemies();
        renderScore();
        m_Level.AdditionalRenderingIfNeeded();
    }

    private void renderEnemies()
    {
        boolean checkIfwaterBalloonHitFireNeeded = false;
        for(Object enemyIter : m_EnemysToDraw)
        {
            enemy = (IEnemy) enemyIter;
            if (enemy.Visible())
            {
                if(!enemy.TouchedGround())
                {
                    if(enemy.GetBound().y < screenRectangel.y ) {
                        enemy.ShowImpact();
                        enemy.setTouchedGround(true);
                        m_NumberOfEnemies--;
                        life_points--;
                        if (life_points <= 0) {  EndGame();  }
                    }
                    else if(!enemy.GetBound().overlaps(screenRectangel) && !screenRectangel.contains(enemy.GetBound()))
                    {
                        enemy.Hide();
                    }
                    else
                    {
                        try
                        {
                            enemy.Move(Gdx.graphics.getDeltaTime());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    if (enemy instanceof BalloonExtinguisher)
                    {
                        checkIfwaterBalloonHitFireNeeded = true;
                        enemyFoCheckingOverlap = enemy;
                    }
                }

                enemy.Draw(m_SpriteBatch);
            }
        }



        if(checkIfwaterBalloonHitFireNeeded)
        {
            itemsToRemoveAfterImpact.clear();
            for(Object enemyIter : m_EnemysToDraw)
            {
                enemy = (IEnemy) enemyIter;
                if(enemy.TouchedGround() && enemyFoCheckingOverlap.GetBound().overlaps(enemy.GetBound()))
                {
                    enemy.Hide();
                    itemsToRemoveAfterImpact.add(enemy);
                }
            }
//            m_score += enemy.Score() * itemsToRemoveAfterImpact.size();
//            m_EnemysToDraw.remove(enemyFoCheckingOverlap);
            m_EnemysToDraw.removeAll(itemsToRemoveAfterImpact);
            extinguishSound.play(1f);

        }
    }

    private void renderScore() {
        scoreText.draw(m_SpriteBatch, (float) 1.0);
    }

    public void HandleSwipeOutcome(Array<Vector2> swipePath)
    {
        // In intro level we teach the user to swipe - thus we dont allow to kill the left most enemy with single touch
        // need to redesign this
        if(m_IntroLevel)
        {
            if(swipePath.size < 3)
            {
                for(Vector2 point : swipePath)
                {
                    if (point.x > Gdx.graphics.getWidth()/2)
                    {
                        break;
                    }
                    else
                    {
                        return;
                    }
                }
            }
            else
            {
                for(Vector2 point : swipePath)
                {
                    if (point.x > Gdx.graphics.getWidth()/2)
                    {
                        return;
                    }
                }
            }
        }

        for(Object enemyIter : m_EnemysToDraw)
        {
            enemy = (IEnemy) enemyIter;
            for(Vector2 point : swipePath)
            {
                if(enemy.GetBound().contains(point.x,point.y) && !enemy.TouchedGround()){
                    if (enemy instanceof BalloonExtinguisher)
                    {
                        enemy.ShowImpact();
                        enemy.OnHitSound();
                        if (life_points < INITIAL_LIFE_POINTS) { life_points++; }
                    }
                    else
                    {
                        enemy.OnHitSound();
                        m_EnemysToDraw.remove(enemy);
                        m_NumberOfEnemies--;
                        updateScore(enemy.Score());
                    }
                    break;
                }
            }
        }
    }

    private void setLevel(int level)
    {
        m_EnemysToDraw.clear();
        m_EnemysWaitingList.clear();

        switch (level) {
            case INTRO_LEVEL:
                m_IntroLevel = true;
                m_Level = new PopTutorial(m_SpriteBatch);
                m_EnemysToDraw = m_Level.InitLevel();
                m_NumberOfEnemies = m_EnemysToDraw.size();
                break;
            case 1:
                m_ActionResolver.ShowBetweenLevelsScreen(m_CurrentLevel-1);
                m_Level = new ExtinguisherTeacher(m_SpriteBatch);
                m_EnemysToDraw = m_Level.InitLevel();
                break;
            case FIRST_LEVEL:
                if (PREFS.getBoolean("show_intro", true)) {
                    PREFS.putBoolean("show_intro", false);
                    PREFS.flush();
                }
                life_points = INITIAL_LIFE_POINTS;
                m_ActionResolver.ShowBetweenLevelsScreen(m_CurrentLevel-1);
                m_Level = new LevelOne(m_SpriteBatch);
                m_EnemysWaitingList = m_Level.InitLevel();
                break;
            case 3:
                m_ActionResolver.ShowBetweenLevelsScreen(m_CurrentLevel-1);
                m_Level = new LevelTwo(m_SpriteBatch);
                m_EnemysWaitingList = m_Level.InitLevel();
                break;
            case 4:
                m_ActionResolver.ShowBetweenLevelsScreen(m_CurrentLevel-1);
                m_Level = new LevelThree(m_SpriteBatch);
                m_EnemysWaitingList = m_Level.InitLevel();
                break;
            default:
                EndGame();
        }
    }

    private void EndGame() {
        m_ActionResolver.ShowGameOver(m_score);
        m_score = 0;
        scoreText.setText("Score: " + this.m_score);
        m_ActionResolver.ShowGameOver(m_score);
        setLevel(2);
    }

    public boolean LevelFinished() {
        IEnemy enemy;

        for (Object iter : m_EnemysToDraw) {
            enemy = (IEnemy) iter;
            if (!enemy.TouchedGround()) {
                return false;
            }
        }
        if (m_EnemysWaitingList.size() > 0)
        {
            return false;
        }
        return true;
    }

    public void HandleLevelFinished()  {
        if(m_IntroLevel)
        {
            m_IntroLevel = false;
        }

        finilizeRenderingAndSetNextLevel();
    }

    private void finilizeRenderingAndSetNextLevel() {
        renderCounter++;
        if (renderCounter == RENDER_LOOPS_AFTER_LAST_ENEMY)
        {
            renderCounter = 0;
            if (m_CurrentLevel == LAST_LEVEL)
            {
                m_CurrentLevel = FIRST_LEVEL; //skip the first 2 tutorial levels
                m_showLoginScreen = true;
                m_score = 0;
                scoreText.setText("Score: " + this.m_score);
            }
            else
            {
                m_CurrentLevel++;
            }
            setLevel(m_CurrentLevel);
        }
    }

    private void updateScore(int toAdd) {
        this.m_score += toAdd;
        scoreText.setText("Score: " + this.m_score);
    }

    private void startDispatchThreads()
    {
        Thread enemyDispatchThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    try {
                        Thread.sleep(m_MsForEnemyDispatch);
                        if(!m_EnemysWaitingList.isEmpty())
                        {
                            enemyInWaitingList = (IEnemy) m_EnemysWaitingList.remove(0);
                            m_EnemysToDraw.add(enemyInWaitingList);
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
        enemyDispatchThread.start();

        Thread populateEnemyDispatchListThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    try {
                        itemsToRemove.clear();
                        for (Object iter : m_EnemysToDraw) {
                            Thread.sleep(1000);
                            enemyInToDrawList = (IEnemy) iter;
                            if (enemyInToDrawList.Visible() == false && !enemyInToDrawList.TouchedGround()) {
                                m_EnemysWaitingList.add(enemyInToDrawList);
                                itemsToRemove.add(enemyInToDrawList);
                                enemyInToDrawList.SetNewPosition();
                                enemyInToDrawList.Show();
                            }
                        }
                        m_EnemysToDraw.removeAll(itemsToRemove);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
        populateEnemyDispatchListThread.start();
    }
}


