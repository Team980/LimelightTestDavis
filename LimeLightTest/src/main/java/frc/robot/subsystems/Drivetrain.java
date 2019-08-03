/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Robot;
import frc.robot.commands.DriveCommand;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * Add your docs here.
 */
public class Drivetrain extends Subsystem {

  private DifferentialDrive drive;
  private boolean hasSeenTarget = false;
  double area;
  //area of target object

  private double xOffset;
  //value between -29 and 29
  //distance between tracking object and center of limelight
  //if no object is tracked, the value is zero

  public final static double TOO_CLOSE = 20;
  public final static double TOO_FAR = 15; 
  public final static double SPEED = 0.5;

  private final static double TARGET_AREA = 17.5;
  private final static double DEADBAND_RADIUS = 2.5;

  private Solenoid shifter;
  
  private boolean trackingComplete;
  private boolean notFound;
   

  private Encoder leftEncoder; 
  private Encoder rightEncoder; 
  //private boolean autoShift; 
  //private double howFar;

  public Drivetrain(){
    drive = new DifferentialDrive(Robot.robotMap.leftDrive, Robot.robotMap.rightDrive);
    shifter = Robot.robotMap.shifter;
    trackingComplete = false; 
    notFound = true; 
    leftEncoder = Robot.robotMap.leftEncoder;       
    rightEncoder = Robot.robotMap.rightEncoder;
  }
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public void driveRobot(double move , double turn){
    drive.arcadeDrive(move, turn);
    
    /* other drive options are:
      drive.tankDrive(leftSideSpeed, rightSideSpeed);
      drive.curvatureDrive(speed,angle,doYouWantCoolturns?); */
   
  }

  public void disable(){
    drive.stopMotor();
  }
  public void tracking2Revised()
  {
    xOffset = Robot.limelight.getTrackingX();

    if(Robot.limelight.hasTarget())//if you see the ball
    {
      hasSeenTarget = true;
      drive.arcadeDrive(SPEED, xOffset/27);
      resetEncoderDistance();
    }
    else
    {
      if(hasSeenTarget)//if you saw the ball
      {
        if(getLeftDistance() + getRightDistance() < 3)//drive forward a foot and a half
        {
          drive.arcadeDrive(0.6,0);
        }
        else//after, stop
        {
        drive.stopMotor();
        }
      }
      else//if you sawn't the ball then spin
      {
        drive.arcadeDrive(0,0.5);
      }
    }
  }

  public void tracking2(){
    xOffset = Robot.limelight.getTrackingX();
    if (Robot.limelight.hasTarget()) {
      double area = Robot.limelight.getTrackingArea();
      notFound = false;
      
      if (area < TOO_FAR) {
        double turnSpeed = xOffset / 27;
        drive.arcadeDrive(SPEED , turnSpeed);
        resetEncoderDistance(); 
      } 
      else {
        drive.stopMotor();  
      }
    } 
    else if (notFound) {
     
      drive.arcadeDrive(0 , 0.5);
    }
    else {
      if (getLeftDistance() < 1.5 || getRightDistance() < 1.5) {
        drive.arcadeDrive(0.6,0);
      }
      else {
        drive.stopMotor();
        trackingComplete = true; 
      }
    }
  }
  
  public void tracking() {
    xOffset = Robot.limelight.getTrackingX();
    area = Robot.limelight.getTrackingArea();  
     
    if (Robot.limelight.hasTarget()) {
      // if (TOO_CLOSE < area && area < TOO_FAR){
      //   drive.arcadeDrive(0, xOffset / 27);
      // } else {
      //   drive.arcadeDrive(Math.signum(area - TOO_FAR)* SPEED, xOffset/27); 
      // }

      double speed = betterSignum(area-TARGET_AREA, DEADBAND_RADIUS)*SPEED;
      //double speed = Math.copySign(roundToZero(area - TARGET_AREA, DEADBAND_RADIUS), SPEED);
      System.out.println(speed);
      double turn = xOffset/27.0;

      drive.arcadeDrive(speed, turn);
        
    } else {
      drive.stopMotor();
  
    }
  }
 
  public double betterSignum(double x, double radius) {
    if (Math.abs(x) < radius) {
      return Math.signum(x);
    } else {
      return 0;
    }
  } 

 public double getLeftDistance(){
    return leftEncoder.getDistance();
  }

  public double getRightDistance(){
    return rightEncoder.getDistance();
  }

  public void resetEncoderDistance(){
    leftEncoder.reset();
    rightEncoder.reset();
  }

  public void stopMotors() {
    drive.stopMotor();
  }

  public void setLowGear () {
    shifter.set(true);
  }

  public void setHighGear () {
    shifter.set(false);
  }

  public boolean getTrackingComplete() {
    return trackingComplete; 
  } 


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new DriveCommand());
  }
}
