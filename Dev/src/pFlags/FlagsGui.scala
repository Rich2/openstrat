/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pFlags
import geom._, pgui._, Colour._

/** A shortcut application to display some flags. */
case class FlagsGui(canv: CanvasPlatform) extends CanvasNoPanels("Flags Gui")
{
  backColour = Gray
   
  val tlFlags = Arr(Armenia, Austria, England, UnitedKingdom, Japan)
  val tlObjs = tlFlags.iMap((el, i) => el.compoundStr.scale(100).topLeftTo(canv.topLeft.subY(i * 110)))

  val trFlags = Arr(Belgium, Chad, France)
  val trObjs = trFlags.iMap((el, i) => el.compoundStr.scale(100).topRightTo(canv.topRight.subY(i * 110)))

  val blFlags = Arr(China, Italy, India)
  val blObjs = blFlags.iMap((el, i) => el.compoundStr.scale(100).bottomLeftTo(canv.bottomLeft.addY(i * 110)))

  val brFlags = Arr(Germany, Germany1871, Ireland, UnitedStates)
  val brObjs = brFlags.iMap((el, i) => el.compoundStr.scale(100).bottomRightTo(canv.bottomRight.addY(i * 110)))

  val starCen = 300 pp 0
  val star: GraphicElems = Arr(Star5.fill(White), Star5.crossLines()).scale(500).slate(starCen)
  val cr = Cross().slate(starCen)
 // val ind = India.compoundStr.scale(800)
  val us = UnitedStates.compoundStr.scale(600)
  val notChanging: Arr[GraphicElem] = tlObjs ++ trObjs ++ blObjs ++ brObjs +- us //+- myr

  mouseUp = (_, li, _) =>
  { val str: String = li.headFoldToString("No clickable object on canvas")
    val tg = TextGraphic(str, 28, 0 pp 100)
    repaint(notChanging +-  tg)
  }

  repaint(notChanging)
}