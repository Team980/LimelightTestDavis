/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Limelight;//unnecessary import


public class VisionTargetTrackCommand extends Command {
  private boolean reachedTarget = false;//all these variables and constants should also be in the subsystem
  private static final double TARGET_AREA = 30;
  private static final double AREA_DEADBAND = 5;
  private static final double FORWARD_SPEED = 0.4;
  

  public VisionTargetTrackCommand() {
    
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.drivetrain);
    requires(Robot.limelight);//should not need, limelight does not have any methods that would need to be interupted.
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
      Robot.limelight.setPipelineIndex(Limelight.VISION_TARGET_PIPELINE_INDEX);//change to Robot.limelight.VISION_TARGET_PIPELINE_INDEX
  }//you could pass an argument when activating this command indicating which pipeline you want to track

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (Robot.limelight.hasTarget()) {//all this code should be in the subsystem. Commands execute methods from the subsystem only
      double area = Robot.limelight.getTrackingArea();
      double xOffset = Robot.limelight.getTrackingX();

      if (isTooClose(area)) {
        Robot.drivetrain.driveRobot(-FORWARD_SPEED, xOffset / 27);

      } else if (isTooFar(area)) {
        Robot.drivetrain.driveRobot(FORWARD_SPEED, xOffset / 27);

      } else {
          // just right
        reachedTarget = true;
      }

    } else {
      Robot.drivetrain.stopMotors();
    }
  }

  public static boolean isTooClose(double area) {
    return area > TARGET_AREA+AREA_DEADBAND/2; 
  }

  public static boolean isTooFar(double area) {
    return area < TARGET_AREA-AREA_DEADBAND/2;
  }



  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return reachedTarget;//have a method in the subsystem that returns the reached target variable for this
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.drivetrain.stopMotors();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
