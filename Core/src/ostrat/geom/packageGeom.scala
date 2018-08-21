/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
/** I chose the package name to not clash with "geometry" that may be use in other libraries This package contains Basic geometry. A number of
 *   implementation Value classes of the Int and Double product classes defined in ostrat. 2d graphical objects for generalised use. They are of 
 *   particular use for the generic canvas based classes defined in pCanv but can be used in any display framework and for printing. */
package object geom
{   
   import math._   
     
   val Vec2Z = Vec2(0, 0)
   val Dist2Z = Dist2(0.km, 0.km)
   val LongD = 2.0 / Cos30   
   val cos30: Double = cos(Pi / 6)
   val LatLong0 = LatLong(0, 0)
   val EarthPolarRadius: Dist = 6356.7523.km
   val EarthEquatorialRadius: Dist = 6378.137.km
   val EarthAvDiameter: Dist = 12742.km
   val EarthAvRadius: Dist = EarthAvDiameter / 2
   type SSet[A] = scala.collection.SortedSet[A]
   type CanvElems = List[CanvElem[_]]
   /** Hopefully this existential syntax baggage will be gone in dotty */
   type CanvO = CanvElem[_]
   implicit class IntGeomImplicit(thisInt: Int)
   {
      def v(y: Double): Vec2 = Vec2(thisInt, y)
      def ° : Angle = Angle(thisInt.radiansToDegrees)
      def km: Dist = Dist(thisInt * 1000)
      def metre: Dist = Dist(thisInt)
      @inline def miles: Dist = Dist(thisInt * 1609.344)
      @inline def millionMiles: Dist = thisInt.miles * 1000000
      def * (operator: Dist): Dist = Dist(thisInt * operator.metres)
      def ll (longDegs: Double): LatLong = LatLong(thisInt.degreesToRadians, longDegs.degreesToRadians)
      def east = Longitude.deg(thisInt)
      def west = Longitude.deg(-thisInt)
      def north = Latitude.deg(thisInt)
      def south = Latitude.deg(-thisInt)
   }  
          
   implicit class DoubleGeomImplicit(thisDouble: Double)
   {
      def v(y: Double): Vec2 = Vec2(thisDouble, y)
      def km: Dist = Dist(thisDouble * 1000)
      def metre = Dist(thisDouble)
      def * (operator: Dist): Dist = Dist(thisDouble * operator.metres)
      @inline def miles: Dist = Dist(thisDouble * 1609.344)
      @inline def millionMiles: Dist = thisDouble.miles * 1000000
      def radians: Angle = Angle(thisDouble)
      def degs: Angle = Angle(thisDouble.degreesToRadians)
      def ll (longDegs: Double): LatLong = LatLong(thisDouble.degreesToRadians, longDegs.degreesToRadians)
      def east = Longitude.deg(thisDouble)
      def west = Longitude.deg(-thisDouble)
      def north = Latitude.deg(thisDouble)
      def south = Latitude.deg(-thisDouble)
   }
   
   implicit class SeqGeomImplicit[A](thisSeq: Seq[A])
   {  def displayFold(f: A => Disp2): Disp2 = thisSeq.map(f).displayFlatten
   }
   
   implicit class DistImplicit(thisDist: Dist)
   {  def / (operand: Dist): Double = thisDist.metres / operand.metres
   }
   
   implicit class OptionGeomImplicit[A](thisOption: Option[A])
   {  def canvObjsPair(f: A => (Seq[CanvElem[_]], Seq[CanvElem[_]])): (Seq[CanvElem[_]], Seq[CanvElem[_]]) = thisOption match
      {
         case Some(a) => f(a)
         case None => (Seq(), Seq())
      }
   }
   
   /** 0 degrees or 0 radians */
   def deg0: Angle = Angle(0)
   /** 30 degrees anti-clockwise or + Pi/6 radians */
   val deg30: Angle = Angle(Pi / 6)
   /** 36 degrees anti-clockwise or + Pi/5 radians */
   val deg36: Angle = Angle(Pi / 5)
   /** 45 degrees anti-clockwise or + Pi/4 radians */
   val deg45: Angle = Angle(Pi / 4)
   /** 60 degrees anti-clockwise or + Pi/3 radians */
   val deg60: Angle  = Angle(Pi / 3)  
   /** 72 degrees anti-clockwise or + Pi2/5 radians */
   val deg72: Angle = Angle(Pi2 / 5)
   /** 90 degrees anti-clockwise or + Pi/2 radians */
   val deg90: Angle = Angle(Pi / 2)  
   /** 120 degrees anti-clockwise or + 2 * Pi/3 radians */
   val deg120: Angle = Angle(2 * Pi / 3)
   /** 135 degrees anti-clockwise or + 3 * Pi/4 radians */
   val deg135: Angle = Angle(3 * Pi / 4)
   /** 150 degrees anti-clockwise or + 5 * Pi/6 radians */
   val deg150: Angle = Angle(5 * Pi / 6)
   /** 180 degrees or Pi radians */
   def deg180: Angle = Angle(Pi)   
}
