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

  public Drivetrain(){
    drive = new DifferentialDrive(Robot.robotMap.leftDrive, Robot.robotMap.rightDrive);
  }
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public void driveRobot(double move , double turn){
    drive.arcadeDrive(move, turn);
  }

  public void disable(){
    drive.stopMotor();
  }

  public void Tracking(){
    if (Robot.limelight.getTrackingX() < -2){
      drive.arcadeDrive(0, -.4);
    }
    else if (Robot.limelight.getTrackingX() > 2){
      drive.arcadeDrive(0, .4);
    }
    else{
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
