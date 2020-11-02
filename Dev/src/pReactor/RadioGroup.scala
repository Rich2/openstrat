package ostrat
package pReactor
import geom._, pCanv._, Colour._

/** Controls a collection of RadioOptions */
case class RadioGroup(selectedIndex:Int = 0, radioOptions:Array[RadioOption], loc:Vec2 = 0 vv 0)
{ def put(aSelected:Int = selectedIndex, aRadioOptions:Array[RadioOption] = radioOptions, loc:Vec2 = loc): Unit =
  { 
  }
}
