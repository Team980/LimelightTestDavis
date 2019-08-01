/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                   `                                            */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class RobotMap {
  public SpeedControllerGroup leftDrive;
  public SpeedControllerGroup rightDrive;
  
  private WPI_VictorSPX leftTop;
  private WPI_VictorSPX leftFront;
  private WPI_VictorSPX leftBack;
 
  private WPI_VictorSPX rightTop;
  private WPI_VictorSPX rightFront;
  private WPI_VictorSPX rightBack;
   
  
  public RobotMap(){
    leftTop = new WPI_VictorSPX(3);
    leftTop.setInverted(true);
    leftFront = new WPI_VictorSPX(1);
    leftBack = new WPI_VictorSPX(4);

    rightTop = new WPI_VictorSPX(2);
    rightTop.setInverted(true);
    rightFront = new WPI_VictorSPX(0);
    rightBack = new WPI_VictorSPX(5);

    leftDrive = new SpeedControllerGroup(leftTop, leftFront, leftBack);
    rightDrive = new SpeedControllerGroup(rightTop, rightFront, rightBack);
  }
}
