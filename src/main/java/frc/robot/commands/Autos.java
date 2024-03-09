package frc.robot.commands;

import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.FloorIntake;
import frc.robot.subsystems.Shooter; 

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.util.ReplanningConfig;

public class Autos {

    private final Drivetrain m_drivetrain;
    private final FloorIntake m_floorIntake;
    private final Shooter m_shooter;

    public Autos(Drivetrain drivetrain, FloorIntake floorIntake, Shooter shooter){
        this.m_drivetrain =  drivetrain;
        this.m_floorIntake =  floorIntake;
        this.m_shooter = shooter;
 
     AutoBuilder.configureRamsete(
        m_drivetrain::getPose, // Robot pose supplier
        m_drivetrain::resetOdometry, // Method to reset odometry (will be called if your auto has a starting pose)
        m_drivetrain::getWheelSpeeds, // Current ChassisSpeeds supplier
        m_drivetrain::driveVoltage, // Method that will drive the robot given ChassisSpeeds
        new ReplanningConfig(), // Default path replanning config. See the API for the options here
        () -> {
          // Boolean supplier that controls when the path will be mirrored for the red alliance
          // This will flip the path being followed to the red side of the field.
          // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

          var alliance = DriverStation.getAlliance();
          if (alliance.isPresent()) {
            return alliance.get() == DriverStation.Alliance.Red;
          }
          return false;
        },
        this.m_drivetrain // Reference to this subsystem to set requirements
     );

    }
}
