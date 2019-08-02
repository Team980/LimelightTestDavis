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
  private double xOffset;

  public final static double CLOSE_ENOUGH_AREA = 20;
  public final static double TOO_FAR_AREA = 15; 
  public final static double FORWARD_SPEED = 0.5;
  private Solenoid shifter;
  
  private boolean trackingComplete;
  private boolean notFound; 

  private Encoder leftEncoder; 
  private Encoder rightEncoder; 
  //private boolean autoShift; 
  //private double howFar;

  public Drivetrain(){
    drive = new DifferentialDrive(Robot.robotMap.leftDrive, Robot.robotMap.rightDrive);
    xOffset = 0.0;
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
  }

  public void disable(){
    drive.stopMotor();
  }

  public void tracking2(){
    xOffset = Robot.limelight.getTrackingX();
    if (Robot.limelight.hasTarget()) {
        double area = Robot.limelight.getTrackingArea();
        notFound = false;
      if (area < TOO_FAR_AREA) {
        System.out.println(" trying to go forward");
        double turnSpeed = xOffset / 27;
        drive.arcadeDrive(FORWARD_SPEED , turnSpeed);
      } 
      else {
        drive.stopMotor();  
      }
    } 
    else if (notFound) {
      System.out.println("no valid targets");
      drive.arcadeDrive(0 , 0.4);
    }
    else {
      resetEncoderDistance(); 
      if (getLeftDistance() < 2 || getRightDistance() < 2) {
        drive.arcadeDrive(7,0);
      }
      else {
        drive.stopMotor();
        trackingComplete = true; 
      }
    }
  }
  
  public void tracking() {
    xOffset = Robot.limelight.getTrackingX();

    if (Robot.limelight.hasTarget()) { // TODO: change this to if (xOffset != 0) { .. } if follow stops working
      double forwardSpeed;
      double area = Robot.limelight.getTrackingArea();
      if (area < TOO_FAR_AREA) {
        System.out.println(" trying to go forward");

        forwardSpeed = FORWARD_SPEED;
      } 
      else if (area > CLOSE_ENOUGH_AREA) {
        forwardSpeed = -FORWARD_SPEED;
      } 
      else {
        forwardSpeed = 0;
      }

      double turnSpeed = xOffset / 27;
      drive.arcadeDrive(forwardSpeed, turnSpeed);
    } 
    else {
      System.out.println("no valid targets");
      drive.stopMotor();
  
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
