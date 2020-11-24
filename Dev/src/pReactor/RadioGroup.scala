package ostrat
package pReactor
import geom._, pCanv._, Colour._

/** Controls a collection of RadioOptions */
case class RadioGroup(radioOptions:Arr[RadioOption], aSelectedIndex:Int)
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

  def put(): GraphicElems =
  { var ret:Arr[GraphicElem] = Arr()
    radioOptions.foreach(radio => { ret = radio.put(this) ++ ret })
    ret
  }
}
