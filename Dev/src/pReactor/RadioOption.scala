/* Copyright 2018-20 w0d. Licensed under Apache Licence version 2.0. */
package ostrat; package pReactor
import geom._, Colour._

/** Simple Radio button with label **/
case class RadioOption(aIsSelected:Boolean = false, labelText:String = "", loc:Pt2 = 0 pp 0, aIsEnabled:Boolean = true)
{ val defaultSize = 12
  var parent:RadioGroup = null
  var isSelected = aIsSelected
  var isEnabled = aIsEnabled

  def toGraphicElems(aParent:RadioGroup, aIsSelected: Boolean = isSelected, labelText: String = labelText, loc:Pt2 = loc, aIsEnabled: Boolean = isEnabled): GraphicElems =
  { isSelected = aIsSelected
    isEnabled = aIsEnabled
    parent = aParent

    val ink = if (isEnabled) White else Grey
    var ret:GraphicElems = RArr(TextGraphic(labelText, defaultSize, loc.addX(defaultSize), ink, LeftAlign))
    if (isSelected) ret = ret ++ RArr(Circle(defaultSize - 4, loc).fill(ink))

    //drawActive with lineWidth 0.01 FUDGE :( todo: flesh out   circle.active(activeId) and circle.drawActive(activeId)
    if (isEnabled) ret ++ RArr(Rect(defaultSize, defaultSize, loc).drawActive(ink, 0.01, this), Circle(defaultSize, loc).draw(ink, 1))
    else ret ++ RArr(Circle(defaultSize, loc).draw(ink, 1))
  }

  def clicked(): Unit = parent.clicked(this)
}