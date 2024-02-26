// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drivetrain m_drivetrain =  new Drivetrain();
  private final Shooter m_shooter =  new Shooter();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  private final PowerDistribution m_pdh = new PowerDistribution();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    m_drivetrain.setDefaultCommand( 
      m_drivetrain.arcadeDrive(
          () -> -m_driverController.getLeftY(), 
          () -> m_driverController.getRightX()
      )
    );
    
     // m_driverController.leftBumper().whileTrue(m_shooter.take());
      //m_driverController.rightBumper().whileTrue(m_shooter.shoot());
   
    SmartDashboard.putData(m_pdh);

  }
}
