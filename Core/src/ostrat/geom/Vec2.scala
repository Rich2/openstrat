/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import math._

/** A 2 dimensional vector, can be used to represent 2 dimensional points and translations of 2 dimensional points. Thanks to Rene Descarte this
 *  was a great idea. */
final class Vec2 (val x: Double, val y: Double) extends ProdD2 //with Stringer
{ override def typeSym = 'Vec2
  override def str: String = persistD2(x, y)
  override def canEqual(other: Any): Boolean = other.isInstanceOf[Vec2]
  @inline override def _1 = x
  @inline override def _2 = y
   
  def strMod(f: Double => String): String = "Vec2".appendParenth(f(x), f(y))
  def str1: String = strMod(_.str1)
  def str2: String = strMod(_.str2)
   def str3: String = strMod(_.str3)
   override def equals(other: Any): Boolean = other match
   {
      case Vec2(px, py) => (x =~ px) && (y =~ py)
      case _ => false
   }   
   def doublesSeq = Seq(x, y)
   def toPair: (Double, Double) = (x, y)
   def +(other: Vec2): Vec2 = Vec2(x + other.x, y + other.y)
   def addXY (otherX: Double, otherY: Double): Vec2 = Vec2(x + otherX, y + otherY)
   def subXY (otherX: Double, otherY: Double): Vec2 = Vec2(x - otherX, y - otherY)
   def -(other: Vec2): Vec2 = Vec2(x - other.x, y - other.y)
   def unary_- : Vec2 = Vec2(-x, -y)
   def *(factor: Double): Vec2 = Vec2(x * factor, y * factor)
   def /(divisor: Double): Vec2 = Vec2(x / divisor, y / divisor)
   def addX(adj: Double): Vec2 = Vec2(x + adj, y)
   def addY(adj: Double): Vec2 = Vec2(x, y + adj)
   def subX(adj: Double): Vec2 = Vec2(x - adj, y)   
   def subY(adj: Double): Vec2 = Vec2(x, y - adj)
   def scaleY(factor: Double): Vec2 = Vec2(x, y * factor)
   def mirrorX: Vec2 = Vec2(-x, y)
   def mirrorY: Vec2 = Vec2(x, -y)
   /** Where xnd y is a map on the surface ofa sphere. Currently not working for angles greater than Pi / 2 */
   def toLatLong(radius: Double): LatLong = LatLong(math.acos(y / radius), math.acos(x / radius))
   
 //  override def persistName: String = "Vec2"   
   /* Reverses the y coordinate. Useful for translating between canvases where the y axis measures down and coordinate systems where y is up */
   def inverseY: Vec2 = Vec2(x, -y)
   def toTuple: Tuple2[Double, Double] = (x, y)
   def v(z: Double): Vec3 = Vec3(x, y, z)
   def magnitude = math.sqrt(x * x + y * y)
   def angle: Angle =
   {
      def at = atan(y / x)
      val r = x match
      {
         case x if x < - 0.000000010 && y < 0 => at - Pi 
         case x if x < - 0.00000001 => Pi + at
         case x if x > 0.00000001 => at
         case _ if y < 0 => -Pi/2
         case _ => Pi/2
      }
      Angle(r)
   }
   def distanceFrom(other: Vec2): Double = math.sqrt({val dim = (x - other.x); dim * dim} + {val dim = (y - other.y); dim * dim})
   
   def rectVerts(width: Double, height: Double): Seq[Vec2] =
   {
      val ax = width / 2
      val ay = height / 2
      Seq(Vec2(x - ax, y + ay), Vec2(x + ax, y + ay), Vec2(x + ax, y -ay), Vec2(x -ax, y -ay))
   }
   def withinRect(target: Vec2, width: Double, height: Double): Boolean =
   {
      val xd: Double = width / 2
      val yd: Double = height / 2
      (x > target.x - xd) && (x < target.x + xd) && (y > target.y - yd) && (y < target.y + yd)
   }
   /** This sure looks right */
   def rotate(a: Angle): Vec2 =  Vec2(x * a.cos - y * a.sin, x * a.sin + y * a.cos)
   def rotateRadians(r: Double): Vec2 = Vec2(x * cos(r) - y * sin(r),
      {
          val ya = x * sin(r)          
          val yb =y * cos(r)          
          ya + yb
      })
   
   def centreSquare(length: Double): Polygon =
   {
      val r = length / 2.0
      Polygon(-r vv r, r vv r, r vv -r, -r vv -r).slate(x, y) 
   }
   
   def fillText(str: String, fontSize: Int, fontColour: Colour = Colour.Black) = TextGraphic(this, str, fontSize, fontColour)
   def arcControlPoint(pt2: Vec2, arcCentre: Vec2): Vec2 =
   {
      val angle1 = (this - arcCentre).angle
      val angle2 = (pt2 - arcCentre).angle
      val resultAngle =  angle1.bisect(angle2)
      val alphaAngle =  resultAngle / 2
      val radius = (pt2 - arcCentre).magnitude
      arcCentre + resultAngle.toVec2 * radius / alphaAngle.cos
   }
   def linesCross(armLength: Double = 5): Seq[Line2] = Seq(Line2(x - armLength, y, x + armLength, y), Line2(x, y - armLength, x, y + armLength))
   def drawCross(armLength: Double = 5, lineColour: Colour = Colour.Black, lineWidth: Double = 2): LinesDraw =
      LinesDraw(lineWidth, lineColour, Line2(x - armLength, y, x + armLength, y), Line2(x, y - armLength, x, y + armLength))
}

object Vec2
{
   def apply(x: Double, y: Double): Vec2 = new Vec2(x, y)
   def unapply(orig: Vec2): Option[(Double, Double)] = Some((orig.x, orig.y))
   def fromAngle(angle: Angle, scalar: Double = 1.0): Vec2 = angle.toVec2 * scalar

   implicit class Vec2Implicit(thisVec: Vec2)
   {
      def * (operand: Dist): Dist2 = Dist2(thisVec.x * operand, thisVec.y * operand)
   }
   def circlePt(angle: Double): Vec2 = Vec2(cos(angle), sin(angle))
   def circlePtClockwise(angle: Double): Vec2 = Vec2(cos(angle), - sin(angle))
   
   implicit class Vec2SeqImplicit(thisSeq: Seq[Vec2])
   {
      def toPolygon: Polygon = thisSeq.toProdD2
   }

  implicit object Vec2Persist extends PersistD2[Vec2]('Vec2, v => (v.x, v.y), apply)
}