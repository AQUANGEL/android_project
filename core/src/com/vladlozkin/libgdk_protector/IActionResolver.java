package com.vladlozkin.libgdk_protector;

public interface IActionResolver {
    public void ShowMainMenu();
    public void ShowLeaderBoard(int score);
    public void ShowGameOver(int score);
    public void ShowBetweenLevelsScreen(int level);
}
