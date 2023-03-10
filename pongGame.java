//NOT the most optimised in the world but I thought it was cool
//run on processing



final int SCREENX = 240;
final int SCREENY = 340;
final int PADDLEHEIGHT = 15;
final int PADDLEWIDTH = 50;
final int MARGIN = 10;

import java.util.Scanner;

int humanXPosition;
int humanYPosition;
int humanLives;
int humanWins;
Player human;

int computerXPosition;
int computerYPosition;
int computerLives;
int computerWins;
Player computer;

boolean displayHuman;
boolean displayComputer;

int ballXPosition;
int ballYPosition;
int ballWidth;
int ballHeight;

boolean goRight;
boolean goLeft;
boolean goUp;
boolean goDown;

boolean dontChangeLivesCounter;

int ballXPositionIncrementer;
int ballYPositionIncrementer;
Ball ball;

int framerate;



class Player {

  public void moveHuman() {
    humanXPosition = mouseX - (PADDLEWIDTH/2);
    noStroke();
    fill(155, 0, 255);
    rect(humanXPosition, humanYPosition, PADDLEWIDTH, PADDLEHEIGHT);
  }


  public void moveComputer() {
    noStroke();
    fill(255, 150, 0);
    rect(computerXPosition, computerYPosition, PADDLEWIDTH, PADDLEHEIGHT);

    float computerSpeed = 1.5;
    switch (computerLives) {
    default:
      computerSpeed = 1.5;
      break;
    case 2:
      computerSpeed = 1.75;
      break;
    case 1:
      computerSpeed = 1.9;
      break;
    }
    text("COMPUTER SPEED: " + computerSpeed+ "pixels per second", 1 * (width/4), MARGIN);

    if (ballXPosition > (computerXPosition+(PADDLEWIDTH/2))) {
      computerXPosition+=computerSpeed;
    } else if (ballXPosition < (computerXPosition+(PADDLEWIDTH/2))) {
      computerXPosition-=computerSpeed;
    }
  }
}

class Ball {

  public void moveBall() {
    noStroke();
    fill(255, 0, 0);
    rect(ballXPosition, ballYPosition, ballWidth, ballHeight);

    if (goDown && goRight) {
      ballXPosition+=2;
      ballYPosition+=2;
    } else if (goDown && goLeft) {
      ballXPosition-=2;
      ballYPosition+=2;
    } else if (goUp && goRight) {
      ballXPosition+=2;
      ballYPosition-=2;
    } else if (goUp && goLeft) {
      ballXPosition-=2;
      ballYPosition-=2;
    }
  }

  public void checkIfBallIsOutOfBounds() {
    if (!dontChangeLivesCounter) {
    
    if (ballYPosition>height) {
      humanLives--;
      text("HUMAN LIVES: " + (humanLives), width/4, (height/2)+20);
      dontChangeLivesCounter = true;

    } else if (ballYPosition<0) {
      computerLives--;
      dontChangeLivesCounter = true;
    }
    
    }
    text("HUMAN LIVES: " + (humanLives), width/4, (height/2)+20);
    text("COMPUTER LIVES: " + computerLives, width/4, (height/2)-20);

    
  }

public void reset() {
  if (humanLives<=0) {
      displayComputer=true;
  } else if (computerLives==0) {
      displayHuman=true;
  }
  


  goDown = true;
  goRight = true;
  goLeft = false;
  goUp = false;

  ballXPosition = width/2;
  ballYPosition = height/2;
  //}
  //input.close();
}



public void checkForCollisionWithHuman() {
  if  (ballYPosition<(height-MARGIN) && (ballYPosition>=(humanYPosition))) {
    if ((ballXPosition>humanXPosition) && (ballXPosition<(humanXPosition+PADDLEWIDTH)))
    {
      goUp = true;
      goDown = false;
      framerate+=5;
      frameRate(framerate); //make the ball go faster, will make human paddle more sensitive to movement too

      //change direction of ball when hit
      if (ballXPosition< (humanXPosition+(PADDLEWIDTH/2)) && goRight) {
        goRight=false;
        goLeft=true;
      } else if (ballXPosition>(humanXPosition+(PADDLEWIDTH/2)) && goLeft) {
        goRight=true;
        goLeft=false;
      }
    }
  }
}

public void checkForCollisionWithComputer() {
  if ( (ballYPosition > MARGIN && ballYPosition<=(PADDLEHEIGHT+MARGIN))
    && (ballXPosition >= computerXPosition && ballXPosition <=(computerXPosition+PADDLEWIDTH)) ) {
    goDown = true;
    goUp = false;
  }
}

public void checkForCollisionWithWalls() {
  if (ballXPosition>=width) {
    goRight=false;
    goLeft=true;
  } else if (ballXPosition<=0) {
    goRight=true;
    goLeft=false;
  }
}


}




void setup() {
  size(240, 340);
  humanYPosition = height - (MARGIN+PADDLEHEIGHT);
  computerYPosition = MARGIN+PADDLEHEIGHT;

  ballWidth = width/48;
  ballHeight = ballWidth; //height/48;
  ballYPosition = height/2;

  human = new Player();
  computer = new Player();
  ball = new Ball();

  goDown = true;
  goRight = true;
  goLeft = false;
  goUp = false;

  humanLives = 3;
  computerLives = 3;
  humanWins = 0;
  computerWins = 0;

  framerate=50;
  frameRate(framerate);
}

void draw() {
  background(0);
  
  
  
  human.moveHuman();
  computer.moveComputer();

  ball.checkForCollisionWithHuman();
  ball.checkForCollisionWithComputer();
  ball.checkForCollisionWithWalls();
  ball.checkIfBallIsOutOfBounds();

  ball.moveBall();
  
  if (displayHuman) {
      text("HUMAN WINS!", width/2, (height/2));
  } else if (displayComputer) {
      text("COMPUTER WINS!", width/2, (height/2));
  }
}

void mousePressed() {
  //text("HUMAN LIVES: " + (3-computerWins), width/4, (height/2)+20);
  //text("COMPUTER LIVES: " + computerLives, width/4, (height/2)-20);
  
  ball.reset();
  dontChangeLivesCounter = false;
}
