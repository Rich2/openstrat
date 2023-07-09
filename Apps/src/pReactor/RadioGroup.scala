/* Copyright 2018-20 w0d. Licensed under Apache Licence version 2.0. */
package ostrat; package pReactor
import geom._

/** Controls a collection of RadioOptions */
case class RadioGroup(radioOptions:RArr[RadioOption], aSelectedIndex:Int)
{ var selected = radioOptions(aSelectedIndex)

  def clicked(targetReference:RadioOption): Unit =
  { if (!targetReference.isSelected)
    { if (selected != targetReference)
      { selected.isSelected = false
        targetReference.isSelected = true
        selected = targetReference
      }
    }
  }
  def setEnabled(newState:Boolean): Unit = radioOptions.foreach(radio => radio.isEnabled = newState)

  def toGraphicElems(): GraphicElems =
  { var ret:RArr[GraphicElem] = RArr()
    radioOptions.foreach(radio => { ret = radio.toGraphicElems(this) ++ ret })
    ret
  }
}