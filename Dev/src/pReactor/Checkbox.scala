/* Copyright 2018-20 w0d. Licensed under Apache Licence version 2.0. */
package ostrat; package pReactor
import geom._, Colour._

/** Simple Checkbox with label **/
case class Checkbox(aIsSelected:Boolean = false, labelText:String = "", loc:Pt2 = 0 pp 0, aIsEnabled:Boolean = false, action: (Checkbox) => Unit = (Checkbox) => {}, myColor:Colour = White) {
  val defaultSize = 12
  var isSelected = aIsSelected
  var isEnabled = aIsEnabled
  var color = myColor

  def toGraphicElems(aIsSelected: Boolean = isSelected, labelText: String = labelText, loc: Pt2 = loc, aIsEnabled:Boolean = isEnabled, aAction: (Checkbox) => Unit = action): GraphicElems =
  { isSelected = aIsSelected
    isEnabled = aIsEnabled
    val ink = if (isEnabled) myColor else Grey

    var ret:GraphicElems = RArr(TextGraphic(labelText, defaultSize, loc.addX(defaultSize), ink, LeftAlign))
    if (isSelected) ret = ret ++ RArr(Rect(defaultSize - 4, defaultSize - 4, loc).fill(ink))
    if (isEnabled) ret ++ RArr(Rect(defaultSize, defaultSize, loc).drawActive(ink, 1, this))
    else ret ++ RArr(Rect(defaultSize, defaultSize, loc).draw(ink, 1))
  }

  def clicked() =
  { if (isEnabled)
    { isSelected = !isSelected
      action(this)
    }
  }
}