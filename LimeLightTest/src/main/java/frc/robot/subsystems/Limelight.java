/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Limelight extends Subsystem {
  public static final double BALL_TARGET_PIPELINE_INDEX = 0;
  public static final double VISION_TARGET_PIPELINE_INDEX = 1;


  NetworkTable table;
  NetworkTableEntry tx;
  NetworkTableEntry ty;
  NetworkTableEntry ta;
  NetworkTableEntry tv;
  NetworkTableEntry pipelineIndex;


  public Limelight(){
    table = NetworkTableInstance.getDefault().getTable("limelight");
    tx = table.getEntry("tx");
    ty = table.getEntry("ty");
    ta = table.getEntry("ta");
    tv = table.getEntry("tv");
  }
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public void setPipelineIndex(double index) {
    pipelineIndex.setDouble(index);
  }

  public boolean hasTarget() {
    return tv.getDouble(0.0) == 1; // is 1 when there is a valid target
  }

  public double getTrackingX(){
    return tx.getDouble(0.0);
  }

  public double getTrackingY(){
    return ty.getDouble(0.0);
  }

  public double getTrackingArea(){
    return ta.getDouble(0.0);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
