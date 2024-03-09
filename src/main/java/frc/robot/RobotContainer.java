// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Shoot;
import frc.robot.commands.SpeakerAuto;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.FloorIntake;
import frc.robot.subsystems.Hanging;
import frc.robot.subsystems.Intake;
//import frc.robot.subsystems.SelfAlignment;
import frc.robot.subsystems.Shooter;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  private final Drivetrain m_drivetrain =  new Drivetrain();
  private final Shooter m_shooter =  new Shooter();
  private final Hanging m_hanging =  new Hanging();
  private final Intake m_intake = new Intake();
  private final FloorIntake m_floorIntake = new FloorIntake();

  private final SendableChooser<Command> autoChooser;


  //private final SelfAlignment m_selfAlighAlignment = new SelfAlignment(m_drivetrain);

  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  private final CommandXboxController m_subsystemController =
      new CommandXboxController(OperatorConstants.kSubsystemControllerPort);

  private final PowerDistribution m_pdh = new PowerDistribution();

  public RobotContainer() {
    m_drivetrain.setDefaultCommand( 
      m_drivetrain.arcadeDrive(
          () -> m_driverController.getLeftY(), 
          () -> m_driverController.getRightX()
      )
    );
    m_subsystemController.rightBumper().onTrue(m_shooter.autoShoot());
    m_subsystemController.leftBumper().whileTrue(m_shooter.take());

    m_subsystemController.b().whileTrue(m_intake.take());
    m_subsystemController.x().whileTrue(m_intake.drop());

    m_subsystemController.y().whileTrue(m_hanging.lift());
    m_subsystemController.a().whileTrue(m_hanging.hang());

    m_subsystemController.axisGreaterThan(0, 0.2).whileTrue(m_floorIntake.take());
    m_subsystemController.axisGreaterThan(0, 0.2).whileTrue(m_floorIntake.drop());

    //m_driverController.a().onTrue(m_selfAlighAlignment.speakerRange());
    m_driverController.leftBumper().whileTrue(m_shooter.take());
    m_driverController.rightBumper().whileTrue(m_shooter.shoot());

    CameraServer.startAutomaticCapture();

    NamedCommands.registerCommand("marker1", Commands.print("Passed marker 1"));
    NamedCommands.registerCommand("marker2", Commands.print("Passed marker 2"));
    NamedCommands.registerCommand("print hello", Commands.print("hello"));

    // Configure the trigger bindings
    configureBindings();

    autoChooser = AutoBuilder.buildAutoChooser(); // Default auto will be `Commands.none()`
    SmartDashboard.putData("Auto Mode", autoChooser);

    SmartDashboard.putData(m_pdh);

    SmartDashboard.putData(CommandScheduler.getInstance());
  }
 
  public Command getAutonomousCommand() {
    /*return new SequentialCommandGroup(
      new WaitCommand(2),
      new SpeakerAuto(m_drivetrain),
      new WaitCommand(2),
      new Shoot(m_shooter),
      new WaitCommand(2),
      new SpeakerAuto(m_drivetrain)
    );
    */

    return autoChooser.getSelected();
  }
}
