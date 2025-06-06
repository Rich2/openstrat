/* Copyright 2018-20 w0d. Licensed under Apache Licence version 2.0. */
package ostrat; package pReactor
import geom.*, Colour.*

/** Simple Radio button with label **/
case class RadioOption(aIsSelected:Boolean = false, labelText:String = "", xLoc: Double = 0, yLoc: Double = 0, aIsEnabled:Boolean = true)
{ def loc: Pt2 = Pt2(xLoc, yLoc)
  val defaultSize = 12
  var parent:RadioGroup = null
  var isSelected = aIsSelected
  var isEnabled = aIsEnabled

  def toGraphicElems(aParent:RadioGroup, aIsSelected: Boolean = isSelected, labelText: String = labelText, loc:Pt2 = loc, aIsEnabled: Boolean = isEnabled):
    GraphicElems =
  { isSelected = aIsSelected
    isEnabled = aIsEnabled
    parent = aParent

    val ink = if (isEnabled) White else Grey
    var ret:GraphicElems = RArr(TextFixed(labelText, defaultSize, loc.slateX(defaultSize), ink, LeftAlign))
    if (isSelected) ret = ret ++ RArr(Circle.d(defaultSize - 4, loc).fill(ink))

    //drawActive with lineWidth 0.01 FUDGE :( todo: flesh out   circle.active(activeId) and circle.drawActive(activeId)
    if (isEnabled) ret ++ RArr(Rect(defaultSize, defaultSize, loc).drawActive(ink, 0.01, this), Circle.d(defaultSize, loc).draw(1, ink))
    else ret ++ RArr(Circle.d(defaultSize, loc).draw(1, ink))
  }

  def clicked(): Unit = parent.clicked(this)
}