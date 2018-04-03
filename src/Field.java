
import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author NAY
 */

public class Field extends JComponent {
    private int width, height, bombs, flags, widthoftile, opened;
    private boolean firstClick, dead, colorfulnumbers;
    private Tile[][] tiles;
    private Color cl;
    //konštruktor
    public Field(JFrame jfr, int x, int y){
        //načíta sa šírka, výška, počet bômb, farba, farebné čísla zo súboru 
        try (BufferedReader file = new BufferedReader(new FileReader("settings.dat"))){
            String[] row = file.readLine().split(";");
            width = Integer.parseInt(row[1]);
            height = Integer.parseInt(row[2]);
            bombs = Integer.parseInt(row[3]);
            switch(row[4]){
                case "0": cl = Color.blue; break;
                case "1": cl = Color.green; break;
                case "2": cl = Color.yellow; break;
                case "3": cl = Color.red; break;
                default: cl = Color.blue;
            }
            colorfulnumbers = !row[5].equals("0");
            file.close();
        } catch(IOException e){
            width = 9;
            height = 9;
            bombs = 10;
            cl = Color.blue;
            colorfulnumbers = true;
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("settings.dat"))){
                bw.write("1;9;9;10;0;1");
                bw.close();
            } catch (IOException ex) {}
        }
        flags = 0;
        opened=0;
        firstClick = false;
        makeField(width, height, bombs);
        setBounds(x, y, width*widthoftile, height*widthoftile);
        jfr.setSize(this.getWidth()+80, this.getHeight()+120);
    }
    //výroba poľa
    private void makeField(int width, int height, int bombs){
        tiles = new Tile[width][height];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                Tile t = new Tile(30 * i, 30*j, 30, cl, colorfulnumbers);
                tiles[i][j]=t;
                add(t);
                widthoftile = t.getWidth();
            }
        }
        placeBombs(bombs); 
        setNumbers();
        repaint();
    }
    //nastavenie bômb
    private void placeBombs(int number_of_bombs){
        for(int i=0;i<number_of_bombs;i++){
            placeBomb();
        }
    }
    //nastaví bombu na náhodnú pozíciu
    private void placeBomb(){
        int x = (int)(Math.random()*width); 
        int y = (int)(Math.random()*height);
        if(!tiles[x][y].isBomb()) tiles[x][y].setBomb(true);
        else placeBomb();
    }
    public void replaceBomb(int x, int y){
        if (x<width && y < height) {
            if (tiles[x][y].isBomb()) {
                placeBomb();
            }
            tiles[x][y].setBomb(false);
            setNumbers();
        }
    } //bomba by nemala nikdy byť prva
    //otvorí pole; ak má pole nulovu hodnotu počtu bômb okolo, rekurziou sa otvárajú ďalšie polia, hranicou sú polia s hodnotou počtu bômb okolo vačšou ako 1 alebo okraj plochy
    private void openTiles(int x, int y){
        tiles[x][y].openTile();
        opened++;
        if(tiles[x][y].getNumberofNearBombs()==0){
            if( x>0        && y>0        && !tiles[x-1][y-1].isFlag() && !tiles[x-1][y-1].isOpen() && tiles[x-1][y-1].getNumberofNearBombs()>=0) openTiles(x-1,y-1);
            if(               y>0        && !tiles[x][y-1].isFlag()   && !tiles[x][y-1].isOpen()   && tiles[x][y-1].getNumberofNearBombs()>=0)   openTiles(x,y-1);
            if( x<width-1  && y>0        && !tiles[x+1][y-1].isFlag() && !tiles[x+1][y-1].isOpen() && tiles[x+1][y-1].getNumberofNearBombs()>=0) openTiles(x+1,y-1);
            if( x>0                      && !tiles[x-1][y].isFlag()   && !tiles[x-1][y].isOpen()   && tiles[x-1][y].getNumberofNearBombs()>=0)   openTiles(x-1,y);
            if( x<width-1                && !tiles[x+1][y].isFlag()   && !tiles[x+1][y].isOpen()   && tiles[x+1][y].getNumberofNearBombs()>=0)   openTiles(x+1,y);
            if( x>0        && y<height-1 && !tiles[x-1][y+1].isFlag() && !tiles[x-1][y+1].isOpen() && tiles[x-1][y+1].getNumberofNearBombs()>=0) openTiles(x-1,y+1);
            if(               y<height-1 && !tiles[x][y+1].isFlag()   && !tiles[x][y+1].isOpen()   && tiles[x][y+1].getNumberofNearBombs()>=0)   openTiles(x,y+1);
            if( x<width-1  && y<height-1 && !tiles[x+1][y+1].isFlag() && !tiles[x+1][y+1].isOpen() && tiles[x+1][y+1].getNumberofNearBombs()>=0) openTiles(x+1,y+1);
        }
    }
    //nastaví každému poľu hodnotu počtu bômb okolo
    private void setNumbers(){
        for(int x=0; x<width; x++){
            for(int y=0; y<height; y++){
                if(!tiles[x][y].isBomb()) tiles[x][y].setNumberofNearBombs(getNumberofNearBombs(x, y));
            }
        }
    }
    //zistenie počtu bômb okolo
    private int getNumberofNearBombs(int x, int y){
        int result = 0;
        if(x>0        && y>0        && tiles[x-1][y-1].isBomb()) result++;
        if(              y>0        && tiles[x][y-1].isBomb())   result++;
        if(x<width-1  && y>0        && tiles[x+1][y-1].isBomb()) result++;
        if(x>0                      && tiles[x-1][y].isBomb())   result++;
        if(x<width-1                && tiles[x+1][y].isBomb())   result++;
        if(x>0        && y<height-1 && tiles[x-1][y+1].isBomb()) result++;
        if(              y<height-1 && tiles[x][y+1].isBomb())   result++;
        if(x<width-1  && y<height-1 && tiles[x+1][y+1].isBomb()) result++;
        return result;
    }
    //umiestni vlajku alebo ju odstráni, podľa toho pridá alebo odoberie z premenej flags
    private void placeFlag(int x, int y){
        if(tiles[x][y].isFlag()) flags--;
        else flags++;
        tiles[x][y].setFlag(!tiles[x][y].isFlag());
    }
    //otvorí všetky polia pri skončení hry
    public void openAllTiles(){
        for(int i=0; i<height; i++){
            for(int j=0; j<width;j++){
                if(!tiles[j][i].isOpen() && tiles[j][i].isBomb()) {
                    tiles[j][i].openTile();
                }
            }
        }
        /*for(int i=0; i<height; i++){
            for(int j=0; j<width;j++){
                if(!tiles[j][i].isOpen()) {
                    tiles[j][i].openTile();
                }
            }
        }*/
    }
    //kliknutie myšou, x,y sú súradníce
    public void leftClick(int x, int y){
        x = (x)/(widthoftile);
        y = (y-5)/(widthoftile);
        if (!firstClick && tiles[x][y].isBomb()) {
            replaceBomb(x, y);
        }
        if (!firstClick) {
            firstClick = true;
        }
        if(x<width && y<height && !tiles[x][y].isFlag() && !tiles[x][y].isOpen()){
            if(!tiles[x][y].isBomb()) openTiles(x, y);
            else {
                tiles[x][y].setStav(4);
                dead = true;
                openAllTiles();
            }
        }
    }
    public void rightClick(int x, int y){
        x = (x)/(widthoftile);
        y = (y-5)/(widthoftile);
        if(x<width && y<height){
            placeFlag(x, y);
        }
    }
    public void mousePress(int x, int y, int oldx, int oldy){
        x = (x)/(widthoftile);
        y = (y-5)/(widthoftile);
        oldx = oldx/widthoftile;
        oldy = (oldy-5)/widthoftile;
        if (!tiles[oldx][oldy].isOpen() && !tiles[oldx][oldy].isFlag()) {
            tiles[oldx][oldy].setStav(0);
        }
        if (!tiles[x][y].isOpen() && !tiles[x][y].isFlag()) {
            tiles[x][y].setStav(1);
        }
    }
    //gettery & settery
    public int getWidthofField() {
        return width;
    }
    public int getHeightofField() {
        return height;
    }
    public int getBombs() {
        return bombs;
    }
    public int getWidthofTile() {
        return widthoftile;
    }
    public boolean getFirstClick(){
        return firstClick;
    }
    public int getFlags(){
        return flags;
    }
    public boolean isFlag(int x, int y){
        x = (x)/(widthoftile);
        y = (y-5)/(widthoftile);
        return tiles[x][y].isFlag();
    }
    public boolean getDead(){
        return dead;
    }
    public int getOpened(){
        return opened;
    }
}
