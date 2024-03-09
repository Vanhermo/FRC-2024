package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConstants;



public class Drivetrain extends SubsystemBase {
    private final CANSparkMax m_frontLeftMotor = new CANSparkMax(DrivetrainConstants.kFrontLeftMotor, MotorType.kBrushless);
    private final CANSparkMax m_frontRightMotor = new CANSparkMax(DrivetrainConstants.kFrontRightMotor, MotorType.kBrushless);
    private final CANSparkMax m_rearLeftMotor =  new CANSparkMax(DrivetrainConstants.kRearLeftMotor, MotorType.kBrushless);
    private final CANSparkMax m_rearRightMotor = new CANSparkMax(DrivetrainConstants.kRearRightMotor, MotorType.kBrushless);   

    private final DifferentialDrive m_drive = new DifferentialDrive(m_frontLeftMotor, m_frontRightMotor);
    
    private RelativeEncoder m_frontLeftEncoder = m_frontLeftMotor.getEncoder();
    private RelativeEncoder m_frontRightEncoder = m_frontRightMotor.getEncoder();
    private RelativeEncoder m_rearLeftEncoder = m_rearLeftMotor.getEncoder();
    private RelativeEncoder m_rearRightEncoder = m_rearRightMotor.getEncoder();

    private final SlewRateLimiter m_forwardLimiter = new SlewRateLimiter(0.5);
  
    private final AHRS navX = new AHRS(SPI.Port.kMXP);
    private Field2d m_field = new Field2d();

    private DifferentialDriveOdometry m_odometry = new DifferentialDriveOdometry(
      new Rotation2d(0),
      0,
      0);
    
    public Drivetrain() {
        m_rearLeftMotor.follow(m_frontLeftMotor);
        m_rearRightMotor.follow(m_frontRightMotor);
        m_frontLeftMotor.setInverted(true);
      }

     public Command arcadeDrive (DoubleSupplier speedProvider, DoubleSupplier rotProvider) {
        return run(() -> {
            double speed = speedProvider.getAsDouble();
            double rot = rotProvider.getAsDouble();
            
            m_drive.arcadeDrive(m_forwardLimiter.calculate(speed), rot * 0.6, true);
        });
      }


    public Command autoDrive (double yValue, double xValue) {
        return run(() -> {
            double speed = yValue;
            double rot = xValue;

            m_drive.arcadeDrive(speed, rot);
        });
      }

      public void driveVoltage(double left, double right){
        m_frontLeftMotor.setVoltage(left);
        m_frontRightMotor.setVoltage(right);
        m_drive.feed();
      }

    public void stop(){
        m_drive.arcadeDrive(0, 0);
      }

    public void drive(double left, double right) {
        m_drive.tankDrive(left, right);
      }

    public double getLeftDistance() {
        return DrivetrainConstants.kDistancePerRev * (m_frontLeftEncoder.getPosition() + m_rearLeftEncoder.getPosition()) / 2;
      }
    
    public double getRightDistance() {
      return DrivetrainConstants.kDistancePerRev * (m_frontRightEncoder.getPosition() + m_rearRightEncoder.getPosition()) / 2;
    }

    public Rotation2d getAngle (){
    return Rotation2d.fromDegrees(navX.getYaw());
    }

    @Override
    public void periodic() {
      m_odometry.update(getAngle(),
          getLeftDistance(),
          getRightDistance());
      m_field.setRobotPose(m_odometry.getPoseMeters());

      SmartDashboard.putNumber("Yaw", getAngle().getDegrees());
      SmartDashboard.putNumber("Pitch", getPitch());
      SmartDashboard.putNumber("Odometry Angle", m_odometry.getPoseMeters().getRotation().getDegrees());
      SmartDashboard.putNumber("Odometry X", m_odometry.getPoseMeters().getX());
      SmartDashboard.putNumber("Odometry Y", m_odometry.getPoseMeters().getY());   
     }

    public double getPitch(){
      return navX.getPitch();
    }
    public Pose2d getPose(){
      return m_odometry.getPoseMeters();
    }
    
    public DifferentialDriveWheelSpeeds getWheelSpeeds(){
      return new DifferentialDriveWheelSpeeds(getLeftDistance(), getRightDistance());
    }

    public void resetOdometry(Pose2d m_Pose2d){
      m_odometry.resetPosition(getAngle(), getLeftDistance(), getRightDistance(), m_Pose2d);
    }

    
}
