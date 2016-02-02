PImage pSlow;
PImage ship;
PImage enemy;
PImage pLife;

//int screenWidth = 1600;
//int screenHeight = 800;

void setup(){
   size(1600, 800);
   background(10, 10, 84);
   pSlow = loadImage("GemBlue.png");
   ship = loadImage("beetleship.png");
   enemy = loadImage("robot_male_1.png");
   pLife = loadImage("Heart.png");
}

int iWidth = 100;  //size of character
int iHeight = 100;
int iX = mouseX-iWidth/2;  //coordinates of character
int iY = mouseY+iHeight/2;
int eWidth = 100;          //enemy size and coordinates
int eHeight = 100;
int eX = 1500;
int eY = 200;
float eSpeed = 6;
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


void draw(){
    background(10, 10, 84);
    textSize(15);
    fill(255, 255, 255);
    noCursor();
    
//    text("Lives: " + lives, 0, 13);
    text("Kills: " + kills, 0, 40);
    text("Click to fire, shoot the robot, move with your mouse", 0, 780);
    
    for(int i = 0; i < lives; i++){
       image(pLife, i*30, 2, 30, 30); 
    }
    
    
    
    if(mousePressed == true && cooldown < limit){   //Fire the laser
        strokeWeight(15);
        stroke(255, 0, 0);
        line(mouseX, mouseY, displayWidth + 100, mouseY);
    }
    image(ship, mouseX-iWidth/2, mouseY-iHeight/2, iWidth, iHeight);
    
    if(spawn == true){  //spawn the enemy
        image(enemy, eX, eY, eWidth, eHeight);
        eX -= eSpeed;
        if(eX < 0){
            lives -= 1;
            eX = displayWidth - (eWidth/2);
        }
    }
    if(mousePressed && eX > mouseX && mouseY > eY && mouseY < eY + eHeight && cooldown < limit && spawn == true){
        spawn = false;
        eX = displayWidth - 10;
        eY = int(random(30, 700));
        kills += 1;
        if(kills>1){
           eSpeed += kills/30;
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
       // text("Cooling down, please cease fire. Degrees over limit: " + (cooldown - 20), 0, 750);
        
        text("OVERHEATING:", 0, 750);
        rect(130, 740, (cooldown - 20), 10);
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
            eSpeed = 4;
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
        rect(-30, -30, displayWidth, displayHeight);
        fill(255, 0, 0);
        rect(100, 100, 1400, 600);
        fill(0, 0, 0);
        textSize(displayWidth/50);
        text("Game Over", displayWidth/4, displayHeight/4);
        text("Points: " + kills, displayWidth/4, displayHeight/4 + 30);
        text("Click here to", displayWidth/4, displayHeight/4 + 60);
        text("Restart", displayWidth/4, displayHeight/4 + 100);
        if(mousePressed && mouseX < 1500 && mouseX > 100 && mouseY < 700 && mouseY > 100){
            lives = 10;
            eSpeed = 4;
            kills = 0;
            playing = 1;
        }
    }
    
    
    
};