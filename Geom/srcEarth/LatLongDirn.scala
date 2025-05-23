/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pglobe

/** Binary trait for north or south up. */
sealed trait NorthSouthUp extends TellSimple
{ override def typeStr: String = "NthSthUp"
  def northUp: Boolean
  def southUp: Boolean
  def rev: NorthSouthUp
  def fld[B](vNorth: => B, vSouth: => B): B
}

object NorthSouthUp
{ implicit val persistEv: PersistBothTellSimple[NorthSouthUp] = PersistBothTellSimple[NorthSouthUp]("NthSthUP", NorthUp, SouthUp)
}

object NorthUp extends NorthSouthUp
{ override def northUp: Boolean = true
  override def southUp: Boolean = false
  override def toString: String = "north-up"
  override def rev: NorthSouthUp = SouthUp
  override def fld[B](vNorth: => B, vSouth: => B): B = vNorth
  override def str: String = "north"
}

object SouthUp extends NorthSouthUp
{ override def northUp: Boolean = false
  override def southUp: Boolean = true
  override def toString: String = "south-up"
  override def rev: NorthSouthUp = NorthUp
  override def fld[B](vNorth: => B, vSouth: => B): B = vSouth
  override def str: String = "south"
}

/** A [[Latitude]] and [[Longitude]] class with a binary north / south direction. */
class LatLongDirn(val latMilliSecs: Double, val longMilliSecs: Double, val dirn: NorthSouthUp) extends LatLongBase
{
  def str: String = latDegStr -- longDegStr

  override def toString: String = latDegStr -- longDegStr

  /** Moves the value northward from this LatLong. This may involve crossing the North Pole or South Pole if the operand is a negative value. When
   * moving across a globe it will often be done using radians as the values come from 3d vector manipulation. */
  override def addLat(delta: AngleVec): LatLongDirn = (latMilliSecs + delta.milliSecs) % MilliSecsIn360Degs match
  { case a if a > MilliSecsIn270Degs => LatLongDirn.milliSecs(MilliSecsIn360Degs - a, longMilliSecs, dirn)
    case a if a > MilliSecsIn90Degs => LatLongDirn.milliSecs(MilliSecsIn180Degs - a, longMilliSecs + MilliSecsIn180Degs, dirn.rev)
    case a if a < -MilliSecsIn270Degs => LatLongDirn.milliSecs(MilliSecsIn360Degs + a, longMilliSecs, dirn)
    case a if a < -MilliSecsIn90Degs => LatLongDirn.milliSecs(-MilliSecsIn180Degs - a, longMilliSecs + MilliSecsIn180Degs, dirn.rev)
    case a => LatLongDirn.milliSecs(a, longMilliSecs, dirn)
  }

  /** Subtract the [[AngleVec]] delta parameter from the latitude. */
  def subLat(delta: AngleVec): LatLongDirn = addLat(-delta)


  /** Add the delta parameter to the longitude. */
  def addLongMilliSeca(delta: Double): LatLongDirn =
  { val long1 = longMilliSecs + delta
    val long2 = long1 %+- MilliSecsIn180Degs
    LatLongDirn.milliSecs(latMilliSecs, long2, dirn)
  }

  def addLongDegs(degsDelta: Double): LatLongDirn = addLongVec(degsDelta.degsVec)

  def subLongDegs(degsDelta: Double): LatLongDirn = addLongDegs(-degsDelta)

  /** Add the [[AngleVec]] delta parameter to the longitude. */
  def addLongVec(delta: AngleVec): LatLongDirn = addLongMilliSeca(delta.milliSecs)

  /** Subtract the [[AngleVec]] delta parameter from the longitude. */
  def subLong(delta: AngleVec): LatLongDirn = addLongVec(-delta)
}

object LatLongDirn
{ /** Factory method for [[LatLong]], creates LatLong from the [[Double]] values for the Latitude and Longitude in degrees, where southern and western
   * values are negative. */
  def degs(lat: Double, long: Double, dirn: NorthSouthUp = NorthUp): LatLongDirn = milliSecs(lat.degsToMilliSecs, long.degsToMilliSecs, dirn)

  /** Factory method for [[LatLong]], creates LatLong from the [[Double]] values for the Latitude and Longitude in thousands of an arc second of a
   * degree, where southern and western values are negative. */
  def milliSecs(lat: Double, long: Double, dirn: NorthSouthUp): LatLongDirn = {
    val lat1 = lat %+- MilliSecsIn180Degs
    val lat2 = lat1 match {
      case l if l > MilliSecsIn90Degs => MilliSecsIn180Degs - l
      case l if l < -MilliSecsIn90Degs => -MilliSecsIn180Degs - l
      case l => l
    }
    val long1 = long %+- MilliSecsIn180Degs
    val long2 = long1 match {
      case lo if lo >= 0 & (lat1 > MilliSecsIn90Degs | lat1 < -MilliSecsIn90Degs) => -MilliSecsIn180Degs + lo
      case lo if lo < 0 & (lat1 > MilliSecsIn90Degs | lat1 < -MilliSecsIn90Degs) => MilliSecsIn180Degs + lo
      case lo => lo
    }
    new LatLongDirn(lat2, long2, dirn)
  }
}
