/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, pglobe._, prid._, phex._

/** An Earth grid covering part of a 30 degrees range of the non polar latitudes. */
trait EGridLongPart extends EGridLong
{
  def fullGrid: EGridLongFull

  override def hCoordLL(hc: HCoord): LatLong = hc.c match {
    case _ if hc.isCen => hCoordMiddleLL(hc)

    case c if c == fullGrid.rowRightCoordC(hc.r, c) => {
      val rt = hCoordMiddleLL(hc)
      val lt = hCoordMiddleLL(HCoord(hc.r, rowLeftCoordC(hc.r, c)))
      val rtLong = rt.longMilliSecs
      val ltLong = (lt.long + 30.east).milliSecs
      val longMilliSecs = rtLong aver ltLong
      LatLong.milliSecs(rt.latMilliSecs, longMilliSecs)
    }

    case c if c == fullGrid.rowLeftCoordC(hc.r, c) => {
      val lt = hCoordMiddleLL(hc)
      val rt = hCoordMiddleLL(HCoord(hc.r, rowRightCoordC(hc.r, c)))
      val ltLong = lt.longMilliSecs
      val rtLong = (rt.long - 30.east).milliSecs
      val longMilliSecs = ltLong aver rtLong
      LatLong.milliSecs(lt.latMilliSecs, longMilliSecs)
    }

    case _ => hCoordMiddleLL(hc)
  }
}
