// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.io.File;
import java.io.IOException;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ModuleConstants;
import swervelib.SwerveDrive;
import swervelib.parser.SwerveParser;
import swervelib.telemetry.SwerveDriveTelemetry;
import swervelib.telemetry.SwerveDriveTelemetry.TelemetryVerbosity;

public class DriveSubsystem extends SubsystemBase {

  /* 
  !!! DOCUMENTATION I USED TO MAKE THIS !!!
  https://yagsl.gitbook.io/yagsl/configuring-yagsl/code-setup
  https://yagsl.gitbook.io/yagsl/bringing-up-swerve/creating-your-first-configuration
  https://github.com/BroncBotz3481/YAGSL-Configs/tree/main

  EDIT CAN IDS AND OTHER MODULE CONSTANTS IN THE DEPLOY DIRECTORY (YAGSL-Test\src\main\deploy\swerve\modules)
  
  */ 
  
  SwerveDrive swerveDrive;
  
  /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {
    File swerveJsons = new File(Filesystem.getDeployDirectory(), "swerve");

  SwerveDriveTelemetry.verbosity = TelemetryVerbosity.HIGH;
    
    try {
      swerveDrive = new SwerveParser(swerveJsons).createSwerveDrive(ModuleConstants.maxSpeed);
    }
    catch (IOException exception) {
      DriverStation.reportError("Error with loading config: ", exception.getStackTrace());
    }
  }

  /**
   * Command to drive the robot using translative values and heading as angular velocity.
   *
   * @param translationX     Translation in the X direction.
   * @param translationY     Translation in the Y direction.
   * @param angularRotationX Rotation of the robot to set
   * @return Drive command.
   */
  public Command driveCommand(DoubleSupplier translationX, DoubleSupplier translationY, DoubleSupplier angularRotationX)
  {
    return run(() -> {
      // Make the robot move
      swerveDrive.drive(new Translation2d(translationX.getAsDouble() * swerveDrive.getMaximumVelocity(),
                                          translationY.getAsDouble() * swerveDrive.getMaximumVelocity()),
                        angularRotationX.getAsDouble() * swerveDrive.getMaximumAngularVelocity(),
                        false,
                        false);
    });
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
