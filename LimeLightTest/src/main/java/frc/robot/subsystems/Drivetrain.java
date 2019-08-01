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

/**
 * Add your docs here.
 */
public class Drivetrain extends Subsystem {
  private DifferentialDrive drive;
  private double xOffset;

  public final static double CLOSE_ENOUGH_AREA = 20;
  public final static double TOO_FAR_AREA = 15; 
  public final static double FORWARD_SPEED = 0.7;

  public Drivetrain(){
    drive = new DifferentialDrive(Robot.robotMap.leftDrive, Robot.robotMap.rightDrive);
    xOffset = 0.0;
  }
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public void driveRobot(double move , double turn){
    drive.arcadeDrive(move, turn);
  }

  public void disable(){
    drive.stopMotor();
  }


  public void tracking() {
    xOffset = Robot.limelight.getTrackingX();


    if (Robot.limelight.hasTarget()) { // TODO: change this to if (xOffset != 0) { .. } if follow stops working
      double forwardSpeed;
      double area = Robot.limelight.getTrackingArea();
      if (area < TOO_FAR_AREA) {
        System.out.println(" trying to go forward");

        forwardSpeed = FORWARD_SPEED;
      } else if (area > CLOSE_ENOUGH_AREA) {
        forwardSpeed = -FORWARD_SPEED;
      } else {
        forwardSpeed = 0;
      }

      double turnSpeed = xOffset / 27;
      drive.arcadeDrive(forwardSpeed, turnSpeed);
    } else {
      System.out.println("no valid targets");

      drive.stopMotor();
    }
  }


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new DriveCommand());
  }
}
