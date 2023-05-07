/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import geom._, pgui._, reflect.ClassTag, Colour.Black

/** A system of Square tile grids. Could be a single or multiple grids. */
trait SqGridSys extends Any with TGridSys
{
  def projection: Panel => SqSysProjection = SqSysProjectionFlat(this, _)

  /** Gives the index into the backing [[Array]] for the given [[SqCen]]. */
  def layerArrayIndex(sc: SqCen): Int

  /** Gives the index into the backing [[Array]] for the given [[SqCen]]. */
  def layerArrayIndex(r: Int, c: Int): Int

  /** Finds step from Start[[SqCen]] to target from[[SqCen]].*/
  def findStepEnd(startSC: SqCen, step: SqStep): Option[SqCen]// = ???

  def findStepEnd(cenStep: SqCenStep): Option[SqCen] = findStepEnd(cenStep.startSC, cenStep.step)
  /** Gives a flat projection of [[SqCoord]]s to [[Pt2]]s. For a simple singular [[SqGrid]] system this is all that is required to translate between
   * grid coordinates and standard 2 dimensional space. For multi grids it provides a simple way to display all the tiles in the grid system, but a
   * more complex projection may be required for fully meaningful display representation. For Example world grid systems and multi layer square tile
   * games will require their own specialist projections. */
  def flatSqCoordToPt2(sqCoord: SqCoord): Pt2

  def foreach(f: SqCen => Unit): Unit

  /** Maps over the [[SqCen]] hex centre tile coordinates. B is used rather than A as a type parameter, as this method maps from HCen => B,
   * corresponding to the standard Scala map function of A => B. */
  final def map[B, ArrB <: Arr[B]](f: SqCen => B)(implicit build: ArrMapBuilder[B, ArrB]): ArrB = {
    val res = build.uninitialised(numTiles)
    var i = 0
    foreach { sqCen => res.setElemUnsafe(i, f(sqCen)); i += 1 }
    res
  }

  /** The active tiles without any PaintElems. */
  def activeTiles: RArr[PolygonActive] = map(_.active())

  /** C coordinates match 1 to 1 to x coordinates for square grids. */
  final override def yRatio: Double = 1

  /** New Square tile data grid for this Square grid system. */
  final def newSCenOptDGrider[A <: AnyRef](implicit ct: ClassTag[A]): SqCenOptLayer[A] = new SqCenOptLayer(new Array[A](numTiles))

  /** Gives the default view in terms of [[SqCoord]] focus and scaling of this square grid system. */
  def defaultView(pxScale: Double = 50): SqGridView

  override final lazy val numTiles: Int =
  { var i = 0
    foreach(_ => i += 1)
    i
  }

  /** Boolean. True if the [[HCen]] hex centre exists in this hex grid. */
  final def sqCenExists(sc: SqCen): Boolean = sqCenExists(sc.r, sc.c)

  def sqCenExists(r: Int, c: Int): Boolean

  /** New Square tile centre data Square grid. */
  final def newSqCenDGrid[A <: AnyRef](value: A)(implicit ct: ClassTag[A]): SqCenLayer[A] = {
    val res: SqCenLayer[A] = SqCenLayer[A](numTiles)
    res.mutSetAll(value)
    res
  }

  /** Creates a new [[SqCenBuffLayer]]. A [[SqCen] square tile centre corresponding Arr of empty [[ArrayBuffer]]s of the given or inferred type. */
  final def newSqCenBuffLayer[A <: AnyRef](implicit ct: ClassTag[A]): SqCenBuffLayer[A] = SqCenBuffLayer(numTiles)

  /** foreach Hex side's coordinate HSide, calls the effectual function. */
  def sidesForeach(f: SqSide => Unit): Unit = ???

  def sideLines: LineSegArr

  /** The line segments of the sides defined in [[SqCoord]] vertices. */
  //def sideLineSegSqCs: LineSegSqCArr = sidesMap(_.lineSegSqC)

  /** This gives the all tile grid lines in a single colour and line width.
   *
   * @group SidesGroup */
  final def sidesDraw(colour: Colour = Black, lineWidth: Double = 2.0): LinesDraw = sideLines.draw(lineWidth, colour)
  /** The line segments of the sides defined in [[SqCoord]] vertices. */
  //def sideLineSegSqCs: LineSegSqCArr = sidesMap(_.lineSegSqC)
}