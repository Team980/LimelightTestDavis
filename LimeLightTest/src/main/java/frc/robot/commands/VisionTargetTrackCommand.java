/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class VisionTargetTrackCommand extends Command {
  private boolean reachedTarget = false;
  private static final double CLOSE_ENOUGH_AREA = 30;
  private static final double FORWARD_SPEED = 0.7;

  public VisionTargetTrackCommand() {
    
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.drivetrain);
    requires(Robot.limelight);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
      Robot.limelight.setPipelineIndex(Robot.limelight.VISION_TARGET_PIPELINE_INDEX);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (Robot.limelight.hasTarget()) {
        double area = Robot.limelight.getTrackingArea();

        if (area >= CLOSE_ENOUGH_AREA) {
            reachedTarget = true;
        }
    } else {
        Robot.drivetrain.driveRobot(0, 0);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return reachedTarget;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
