/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCanv

/** Not sure aboout this class */
abstract class MenuContextGraphic(seq: MenuSeq, x: Double, y: Double)// extends GraphicMethods
//{
//   val backgroundColour = Colour.White
//   val textColour = Colour.Black
//   
//   //def setStrokeColour(colour: Colour): Unit
//   
//   displaySeq(seq, x, y)
//   def displaySeq(seqIn: MenuSeq, x: Double, y: Double): Unit =
//   {
//      val seq: MenuSeq = seqIn.ifEmpty(Seq(MenuLeaf("No Options")), seqIn) 
//      val length: Int = 8 * seq.maxBy(_.text.length).text.length
//      
//      setFillColour(backgroundColour)
//      fillRectangle(x, y, length, 21 * seq.length)
//      for (i <- 0 until seq.length)
//      {
//         val item = seq(i)
//         val yPosn = y + i * 21
//         displayItem(item.text, x, yPosn )
//         item match
//         {
//            case b: MenuBranch => displaySeq(b.nodes, x + length, yPosn)
//            case _ =>
//         }         
//      }
//      def displayItem(text: String,x: Double, y: Double): Unit =
//      {
//         
//         //setStrokeColour = textColour
//         //gc.lineWidth = 1            
//         setFillColour(Colour.Black)
//         setFont(13)
//         fillText(text, x + 2, y + 15)
//      }
//   }
//}