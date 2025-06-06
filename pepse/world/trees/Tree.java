package world.trees;

import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import util.ColorSupplier;

import java.awt.*;
import java.util.Random;

public class Tree {
    private static Color barkColor = new Color(100,50,20);
    private static int barkWidth = 30;
    private static final int treeRows = 12;
    private static final int treeColumns = 9;
    private static final float leafCreationLikelihood = 0.7f;
    private static final int baseTreeHeight = 300;
    private static Random rand = new Random();


    public static GameObject createBark(Vector2 topLeftCorner){
        RectangleRenderable barkRenderable = new RectangleRenderable(ColorSupplier.approximateColor(barkColor));
        Vector2 barkDimensions = new Vector2(barkWidth, baseTreeHeight+rand.nextInt(50));
        GameObject bark = new GameObject(topLeftCorner, barkDimensions, barkRenderable);
        bark.setTag("bark");
        //createTreeTop(topLeftCorner);
        return bark;
    }

    public static GameObject[][] createTreeTop(Vector2 topLeftCorner){
        GameObject[][] treeTop = new GameObject[treeRows][treeColumns];
        Vector2 leafDimensions = Leaf.GetLeafDimensions();
        Vector2 leafTopLeftCorner = new Vector2(
                topLeftCorner.x()-(leafDimensions.x()*(treeColumns/2)),
                topLeftCorner.y()-(leafDimensions.y()*(treeRows/2)));
        for (int i = 0; i < treeRows; i++) {
            for (int j = 0; j < treeColumns; j++) {
                if (Math.random() < leafCreationLikelihood) {
                    float x = topLeftCorner.x() - (leafDimensions.x() * (treeColumns / 2f)) + j * leafDimensions.x();
                    float y = topLeftCorner.y() - (leafDimensions.y() * (treeRows / 2f)) + i * leafDimensions.y();
                    Vector2 leafPos = new Vector2(x, y);
                    treeTop[i][j] = Leaf.createLeaf(leafPos);
                }
            }
        }
        return  treeTop;
    }
    public static int getTreeTopWidth(){
        return treeColumns;
    }
    public static int getTreeTopHeight(){
        return treeRows;
    }

}
