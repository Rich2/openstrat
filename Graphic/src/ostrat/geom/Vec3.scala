/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import math._

/** A 3 dimensional vector, can be used to represent 3 dimensional points and translations of 3 dimensional points. Right-handed coordinate
 *  system is the default */
final class Vec3 (val x: Double, val y: Double, val z: Double) extends ProdD3// with Stringer
{ def typeStr: String = "Vec3"
  //def str = persistD3(x, y, z)
  override def canEqual(other: Any): Boolean = other.isInstanceOf[Vec3]
  def _1 = x
  def _2 = y
  def _3 = z
  override def equals(other: Any): Boolean = other match
  { case Vec3(px, py, pz) => (x =~ px) && (y =~ py) && (z =~ pz)
    case _ => false
  }   
  def str1: String = "x: " - x.str1 - ", y: " - y.str1 - ", z: " - z.str1
  def toTriple: (Double, Double, Double) = (x, y, z)   
  def +(other: Vec3): Vec3 = Vec3(x + other.x, y + other.y, z + other.z)
  def addXYZ (otherX: Double, otherY: Double, otherZ: Double): Vec3 = Vec3(x + otherX, y + otherY, z + otherZ)
  def toXY: Vec2 = Vec2(x, y)
  def toXYIfZPositive: Option[Vec2] = ife(z > 0, Some(Vec2(x, y)), None)
  def xRotation(rotation: Double): Vec3 =
  { val scalar = sqrt(y * y + z * z)
    val ang0 = Unit match
    { //As y and z are both negative, the atan will give a positive value added to -Pi gives range -Pi / 2 to - Pi
      case _ if z < 0 && y < 0 =>  - Pi  + atan(y / z)
      //The atan will give a negative value. Added to Pi gives a range Pi/2 to Pi
      case _ if z < 0 => Pi + atan(y / z)
      //This operates on the standard atan range -Pi/2 to pi/2
      case _ => atan(y / z)
    }
      
    val ang1 = ang0 + rotation
    Vec3(x, sin(ang1) * scalar, cos(ang1) * scalar)      
  }   
   
//   def subXY (otherX: Double, otherY: Double): Vec2 = Vec3(x - otherX, y - otherY)
//   def -(other: Vec2): Vec2 = Vec2(x - other.x, y - other.y)
//   def unary_- : Vec2 = Vec2(-x, -y)
//   def *(factor: Double): Vec2 = Vec2(x * factor, y * factor)
//   def /(divisor: Double): Vec2 = Vec2(x / divisor, y / divisor)
//   def addX(adj: Double): Vec2 = Vec2(x + adj, y)
//   def addY(adj: Double): Vec2 = Vec2(x, y + adj)
//   def subX(adj: Double): Vec2 = Vec2(x - adj, y)   
//   def subY(adj: Double): Vec2 = Vec2(x, y - adj)
//   def scaleY(factor: Double): Vec2 = Vec2(x, y * factor)
//   def mirrorX: Vec2 = Vec2(-x, y)
//   def mirrorY: Vec2 = Vec2(x, -y)
//   override def persistMems: Seq[Persist] = Seq(x, y, z)
//   override def persistName: String = "Vec3"   
//   /* Reverses the y coordinate. Useful for translating between canvases where the y axis measures down and coordinate systems where y is up */
//   def inverseY: Vec2 = Vec2(x, -y)
//   def toTuple: Tuple2[Double, Double] = (x, y)
//   def vecLength = math.sqrt(x * x + y * y)
//   def vecAngle: Angle =
//   {
//      def get(x: Double, y: Double): Double = ife(x < 0.00000001, Pi / 2, atan(y / x))
//      val r = x match
//      {
//         case x if x < 0 & y < 0 => get(x, y) + Pi 
//         case x if x < 0 => Pi - get(x, y)
//         case _ => get(x, y)
//      }
//      Angle(r)
//   }
//   def distanceFrom(other: Vec2): Double = math.sqrt({val dim = (x - other.x); dim * dim} + {val dim = (y - other.y); dim * dim})
//   def rectVerts(width: Double, height: Double): Seq[Vec2] =
//   {
//      val ax = width / 2
//      val ay = height / 2
//      Seq(Vec2(x - ax, y + ay), Vec2(x + ax, y + ay), Vec2(x + ax, y -ay), Vec2(x -ax, y -ay))
//   }
//   def withinRect(target: Vec2, width: Double, height: Double): Boolean =
//   {
//      val xd: Double = width / 2
//      val yd: Double = height / 2
//      (x > target.x - xd) && (x < target.x + xd) && (y > target.y - yd) && (y < target.y + yd)
//   }   
//   def rotate(angle: Angle): Vec2 = angle.radians match
//   {
//     case 0 => Vec2.this
//     case a => Vec2(x * cos(a) - y * sin(a), x * sin(a) + y * cos(a))
//   }
//  
//   //def trans(aff: AffD2): Vec2 = aff(this)
//   def centreSquare(length: Double): Vec2s =
//   {
//      val r = length / 2.0
//      Vec2s(addXY(-r, r), addXY(r, r), addXY(r, -r), addXY(-r, -r)) 
//   }
//   override def numOfPts = 1
//   override def seqOfPts: Vec2s = Vec2s.xy(x, y)
//   def fillText(str: String, fontSize: Int, fontColour: Colour = Colour.Black) = FillText(this, str, fontSize, fontColour)
}


object Vec3
{ def apply(x: Double, y: Double, z: Double): Vec3 = new Vec3(x, y, z)
  def unapply(orig: Vec3): Option[(Double, Double, Double)] = Some((orig.x, orig.y, orig.z))
  
  implicit object Vec3Persist extends PersistD3[Vec3]("Vec3", v => (v.x, v.y, v.z), apply)
//   implicit class Vec3SeqImplicit(thisSeq: Seq[Vec3])
//   {
//      /** Returns Some z positive points if 3 or more */ 
//      def if3ZPositive: Option[Vec2s] = 
//      {
//      def loop(rem: Seq[Vec3], acc: Seq[Vec2]): Seq[Vec2] = rem.fHead(acc, (h, tail)=> h match
//         {
//            case h if h.z > 0 => loop(tail, acc :+ Vec2(h.x, h.y))                  
//            case _ => loop(tail, acc)
//         })
//      val res: Seq[Vec2] = loop(thisSeq, Seq())
//      ifSome(res.length > 2, res)   
//      }
//   } 
}
