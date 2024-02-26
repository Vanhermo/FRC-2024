// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class DrivetrainConstants{
    public static final int kFrontLeftMotor = 2;
    public static final int kFrontRightMotor = 4;
    public static final int kRearLeftMotor = 3;
    public static final int kRearRightMotor = 5;

    public static final double kP = 0;
    public static final double kI = 0;
    public static final double kD = 0;

    public static final double kWheelRadius =  Units.inchesToMeters(3);
    public static final double kGearbox = 8.45;

    public static final double kDistancePerRev = ( 2 * Math.PI * kWheelRadius) / kGearbox;
    public static final double kTrackWidth = 0.56;

    public static final DifferentialDriveKinematics kKinnematics =  new DifferentialDriveKinematics(kTrackWidth);
    public static final double kMaxInputPercent = 0.8;
  }

  /*public static class ShooterConstants {
    public static final int kShooterTopMotor = 0;
    public static final int kShooterBottomMotor = 0;
  }

  public static class HangingConstants {
    public static final int kHangingLeftMotor = 0;
    public static final int kHangingRightMotor = 0;
  }
 */
}
