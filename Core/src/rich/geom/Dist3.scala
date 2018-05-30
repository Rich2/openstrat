/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package geom
import math._

final class Dist3(val xMetres: Double, val yMetres: Double, val zMetres: Double) extends ProdD3
{
   override def canEqual(other: Any): Boolean = other.isInstanceOf[Dist3]
   def _1 = xMetres
   def _2 = yMetres
   def _3 = zMetres
   def x: Dist = Dist(xMetres)
   def y: Dist = Dist(yMetres)
   def z: Dist = Dist(zMetres)
   def xy: Dist2 = new Dist2(xMetres, yMetres)
   def xPos: Boolean = x.pos
   def xNeg: Boolean = x.neg
   def yPos: Boolean = y.pos
   def yNeg: Boolean = y.neg
   def zPos: Boolean = z.pos
   def zNeg: Boolean = z.neg
   def ifZPos[A](vPos: => A, vNeg: => A): A = ife(zPos, vPos, vNeg) 
   def / (operator: Dist): Vec3 = Vec3(x / operator, y / operator, z / operator)
   def toXYIfZPositive: Option[Dist2] = ifZPos(Some(Dist2(x, y)), None)
   def xRotation(rotation: Double): Dist3 = 
   {     
      val scalar: Dist = Dist(sqrt(y.metres * y.metres + z.metres * z.metres))
      if(scalar > EarthEquatorialRadius * 1.05) throw excep("scalar: " - scalar.toString) 
      val ang0 = Unit match
      { 
         //As y and z are both negative, the atan will give a positive value added to -Pi gives range -Pi / 2 to - Pi
         case _ if z.neg && y.neg => atan(y / z) - Pi
         
         //The atan will give a negative value. Added to Pi gives a range Pi/2 to Pi
         case _ if z.neg => Pi + atan(y / z)
         //This operates on the standard atan range -Pi/2 to pi/2
         case _ => atan(y / z)
      }
      
      val ang1 = ang0 + rotation
      Dist3(x, sin(ang1) * scalar, cos(ang1) * scalar)      
   }   
}

object Dist3
{
   var counter = 0
   def metres(xMetres: Double, yMetres: Double, zMetres: Double): Dist3 = new Dist3(xMetres, yMetres, zMetres)
   def apply(x: Dist, y: Dist, z: Dist): Dist3 = new Dist3(x.metres, y.metres, z.metres)
   
}



