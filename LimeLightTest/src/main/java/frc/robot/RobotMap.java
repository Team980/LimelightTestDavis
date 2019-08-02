/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                   `                                            */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.CounterBase;


public class RobotMap {
  public SpeedControllerGroup leftDrive;
  public SpeedControllerGroup rightDrive;
  
  private WPI_VictorSPX leftTop;
  private WPI_VictorSPX leftFront;
  private WPI_VictorSPX leftBack;
 
  private WPI_VictorSPX rightTop;
  private WPI_VictorSPX rightFront;
  private WPI_VictorSPX rightBack;
   
  public Solenoid shifter; 

  public Encoder leftEncoder;
  public Encoder rightEncoder;

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

    shifter = new Solenoid (0); 

    leftEncoder = new Encoder(7 , 8 , false , CounterBase.EncodingType.k4X);
    //(Channel A port , Channel B port , is it inverted true/false , encoder type)
    leftEncoder.setDistancePerPulse((2 * (Math.PI) * (2.0 / 12)) / 2048.0);

    rightEncoder = new Encoder(4 , 5 , true , CounterBase.EncodingType.k4X);
    //(Channel A port , Channel B port , is it inverted true/false , encoder type)
    rightEncoder.setDistancePerPulse((2 * (Math.PI) * (2.0 / 12)) / 2048.0);

  }
}
