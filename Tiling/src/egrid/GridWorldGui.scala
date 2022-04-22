/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import  pgui._, geom._, prid._, phex._, pEarth._, pglobe._

case class GridWorldGui(canv: CanvasPlatform, viewIn: HGridView) extends CmdBarGui("Grid World")
{
  val northUp = true
  var scale: Length = 12.kMetres
  val focus = 0 ll 0

  def repaint(): Unit =
  { val eas: Arr[EArea2] = EarthAreas.allTops.flatMap(_.a2Arr)

    val afps: Arr[(EArea2, PolygonM)] = eas.map { a =>
      val p3s0: PolygonM3 = a.polygonLL.metres3Default
      val p3s1 = p3s0.fromLatLongFocus(focus)
      val p3s2: PolygonM3 = ife(northUp, p3s1, p3s1.rotateZ180)
      val p3s3 = p3s2.earthZPosXYModify
      (a, p3s3)
    }

    val afps2 = afps.filter(_._2.vertsMin3)
    val af0 = afps2.map { pair =>
      val (d, p) = pair
      p.map(_ / scale).fillActive(d.colour, d)
    }

    val af1 = afps2.map { a => a._2.map(_ / scale).draw() }
    val af2 = afps2.map { pair =>
      val (d, _) = pair
      val posn = d.cen.toMetres3.fromLatLongFocus(focus).xy / scale
      TextGraphic(d.name, 10, posn, d.colour.contrastBW)
    }

    def seas = earth2DEllipse(scale).fill(Colour.DarkBlue)

    mainRepaint(seas %: af0 ++ af1 ++ af2)
  }

  repaint()
}