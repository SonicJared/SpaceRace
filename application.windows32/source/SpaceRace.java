import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class SpaceRace extends PApplet {

PImage pSlow;
PImage ship;
PImage enemy;
PImage pLife;

public void setup(){
   
   background(10, 10, 84);
   pSlow = loadImage("GemBlue.png");
   ship = loadImage("beetleship.png");
   enemy = loadImage("robot_male_1.png");
   pLife = loadImage("Heart.png");
}

int iWidth = 100;
int iHeight = 100;
int iX = mouseX-iWidth/2;
int iY = mouseY+iHeight/2;
int eWidth = 100;
int eHeight = 100;
int eX = 350;
int eY = 200;
int eSpeed = 4;
int spawnRate = 157;
int spawnTime = 230;
int spawnSpeed = 10;
boolean spawn = false;
int playing = 0;
int kills = 0;
int lives = 10;
int cooldown = 0;
int limit = 20;
int powerup = 0;
int powerup1 = 0;
int powerup2 = 0;
int pX = 0;
int pY = 0;


public void draw(){
    background(10, 10, 84);
    
    
    textSize(10);
    fill(255, 255, 255);
    text("Lives: " + lives, 0, 13);
    text("Kills: " + kills, 0, 30);
    text("Click to fire, shoot the robot, move with your mouse", 0, 385);
    
    
    
    if(mousePressed == true && cooldown < limit){   //Fire the laser
        strokeWeight(15);
        stroke(255, 0, 0);
        line(mouseX, mouseY, 500, mouseY);
    }
    image(ship, mouseX-iWidth/2, mouseY-iHeight/2, iWidth, iHeight);
    
    if(spawn == true){  //spawn the enemy
        image(enemy, eX, eY, eWidth, eHeight);
        eX -= eSpeed;
        if(eX < 0){
            lives -= 1;
            eX = 390;
        }
    }
    if(mousePressed && eX > mouseX && mouseY > eY && mouseY < eY + eHeight && cooldown < limit && spawn == true){
        spawn = false;
        eX = 350;
        eY = PApplet.parseInt(random(30, 326));
        kills += 1;
        if(kills>1){
            eSpeed += kills/50;
        spawnSpeed += kills/50;
        powerup1 += 1;
        powerup2 += 1;
        
        }
    }
    if(mousePressed){ //cooldown for the laser
        cooldown +=1;
    }else if(cooldown > 0){
        cooldown -= 1;
    }
    if(cooldown > limit){
        text("Cooling down, please cease fire. Degrees over limit: " + (cooldown - 20), 0, 40);
    }
    
    if(spawnRate > spawnTime){
        spawn = true;
        spawnRate = 0;
    }
    
    
    
    if(powerup1 > 20){ //health powerup
        pX = 40;
        pY = 40;
        image(pLife, pX, pY, 30, 30);
        
        if(mouseX > pX && mouseX < pY + 30 && mouseY > pY && mouseY < pY + 30){
            lives += 3;
            powerup1 = 0;
        }
    }
    
    if(powerup2 > 25){ //slow-down powerup
        pX = 143;
        pY = 182;
        image(pSlow, pX, pY, 30, 30);
        
        if(mouseX > pX && mouseX < pY + 30 && mouseY > pY && mouseY < pY + 30){
            eSpeed = 1;
            powerup2 = 0;
        }
    }
    
    
    spawnRate += spawnSpeed;
    
    if(lives < 1){ //endgame
        eSpeed = 0;
        spawn = false;
        powerup1 = 0;
        powerup2 = 0;
        fill(0, 0, 0);
        rect(-30, -30, 450, 450);
        fill(255, 0, 0);
        rect(100, 100, 200, 150);
        fill(0, 0, 0);
        textSize(30);
        text("Game Over", 119, 122);
        text("Points: " + kills, 126, 156);
        text("Click here to", 115, 202);
        text("Restart", 149, 230);
        if(mousePressed && mouseX < 300 && mouseX > 100 && mouseY < 250 && mouseY > 100){
            lives = 10;
            eSpeed = 4;
            kills = 0;
            playing = 1;
        }
    }
    
    
    
};
  public void settings() {  size(400, 400); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "SpaceRace" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
