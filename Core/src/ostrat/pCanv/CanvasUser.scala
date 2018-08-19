/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCanv
import geom._

trait CanvUser
{   
   val canv: CanvasPlatform
   /** This reverses the order of the ClickObj List. Method paints objects to screen as side effect. */
   def paintObjs(movedObjs: Seq[CanvObj[_]]): List[ClickObj] =
   {
      var subjs: List[ClickObj] = Nil
      movedObjs.foreach(_ match
      {
         //case ce: ClickEl[_] => pan.subjsAdd(ce.clickObj)
         case ce: CanvEl[_] => canv.rendElem(ce)
         case cs: CanvSubj[_] =>
         {
            cs.elems.foreach(_ match
                  {
               case FillTextRel(str, fontSize, fontColour, posn, align) => canv.rendElem(FillText(cs.cen + posn, str, fontSize, fontColour, align))
               case el => canv.rendElem(el)
                  })
            canv.rendElems(cs.elems)
            subjs ::= cs
         }
         case nss: NoScaleShape => 
         {             
            canv.rendElems(nss.elems.slate(nss.referenceVec)/*.asInstanceOf[List[CanvEl[_]]]*/)
            subjs ::= nss
            deb("Add Fixed Shape" -- subjs.length.toString -- nss.shape.map(_.endPt).toString)
         }
      })
      subjs
   }
   
   def refresh(): Unit
   
   canv.resize = () => refresh()   
}