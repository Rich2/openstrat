package ostrat
package pReactor
import geom._, pCanv._, Colour._

/** Controls a collection of RadioOptions */
case class RadioGroup(radioOptions:Arr[RadioOption], aSelected:RadioOption)
{ var selected = aSelected

 def clicked(targetReference:RadioOption): Unit =
 { if (!targetReference.isSelected)
   { if (selected != targetReference)
     selected.isSelected = false
     targetReference.isSelected = true
     selected = targetReference
   }
 }
}
