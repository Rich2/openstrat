/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** 3 dimensional line segment measured in metres. A straight line between two points in 3d. */
class LineDist3(xStartMetres: Double, yStartMetres: Double, zStartMetres: Double,
    xEndMetres: Double, yEndMetres: Double, zEndMetres: Double) extends ProdDbl6 //with Stringer
{ def typeStr: String = "LineDist3"
  //def str: String = persist2(pStart, pEnd)
  def pStart: Metres3 = new Metres3(xStartMetres, yStartMetres, zStartMetres)
  def pEnd: Metres3 = new Metres3(xEndMetres, yEndMetres, zEndMetres)
  override def canEqual(other: Any): Boolean = other.isInstanceOf[LineDist3]
  override def _1 = xStartMetres
  override def _2 = yStartMetres
  override def _3 = zStartMetres
  override def _4 = xEndMetres
  override def _5 = yEndMetres
  override def _6 = zEndMetres
  def xStart: Metres = Metres(xStartMetres)
  def yStart: Metres = Metres(yStartMetres)
  def zStart: Metres = Metres(zStartMetres)
  def xEnd: Metres = Metres(xEndMetres)
  def yEnd: Metres = Metres(yEndMetres)
  def zEnd: Metres = Metres(zEndMetres)
  def zsPos: Boolean = zStart.pos && zEnd.pos
  def toXY: LineSegDist = new LineSegDist(xStartMetres, yStartMetres, xEndMetres, yEndMetres)
}

object LineDist3
{
   def apply(pStart: Metres3, pEnd: Metres3): LineDist3 = new LineDist3(pStart.xMetres, pStart.yMetres, pStart.zMetres,
       pEnd.xMetres, pEnd.yMetres, pEnd.zMetres)
}