/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** A trait that persists multiple tokens that may require multiple lines */
trait PersistCompoundOld[R] extends Persist[R]
{
   //val persistMems: Seq[Persist[_]]
   //val persistName: String 
   
   //override def persistDepth: Int =  persistMems.map(_.persistDepth).max + 1 // match
//   {
//      case Seq() => 2          
//      case mems if mems.forall(_.persistDepth == 1) && mems.tail.foldLeft(mems.head.persist)(_ - ", " -- _.persist).length.isShort => 2      
//      case _ => 3
//   }
   
//   override def persistComma: Seq[String] = persistDepth match
//   {
//      case d if d > 2 => persistFull
//      case _ =>
//         {
//            val str = persistMems.commaFold(_.persist)
//            val str2 = ife(persistMems.length == 1, str - ",", str)
//            Seq(str2)   
//         }
//   }
//   
//   def persistFull: Seq[String] =
//   {      
//      def contLine(rem: Seq[Persist[_]], acc: Seq[String], currLine: String): Seq[String] = rem.fHead(
//         Seq(persistName) ++ Seq("(" -- acc.head) ++ acc.tail ++ Seq(currLine, ")"),
//         (h, tail) => h.persistComma match
//         {
//            
//            case Seq(h) if (currLine.length + 2 + h.length).isShort => contLine(tail, acc, currLine -- h - ";")
//            case Seq(h) => contLine(tail, acc :+ currLine, h.ind2 - ";")
//            case s => multiLine(tail, acc ++ s.map(_.ind2))
//         }
//         )
//      def multiLine(rem: Seq[Persist[_]], acc: Seq[String]): Seq[String] = rem.fHead(
//            Seq(persistName) ++ acc,
//            (h, tail) => h.persistComma match
//            {
//               case Seq(h) => contLine(rem, acc, h.ind2 - ";")
//               case s => multiLine(tail, acc ++ s.map(_.ind2 - ";"))
//            }
//         )
//      
//      persistMems match
//      {
//         case Seq() => Seq(persistName + "()")
//         case mems if mems.forall(_.persistComma.length == 1) && (mems.map(_.persistComma.head.length).sum <= maxLen) => mems.last.persistComma.head match
//         {
//            case "" =>
//               {
//                  val persistedMems: Seq[String] = mems.map(_.persistComma.head - ";")
//                  val persistedMemsStr: String = persistedMems.strFold(" ")
//                  Seq(persistName - persistedMemsStr.enParenth)
//               }
//            case lastStr =>
//               {
//                  val persistedMems: Seq[String] = mems.init.map(_.persistComma.head - ";").:+(lastStr)
//                  val persistedMemsStr: String = persistedMems.strFold(" ")
//                  Seq(persistName - persistedMemsStr.enParenth) 
//               }
//         }
//         case Seq(mem1, tail @ _*) => mem1.persistComma match
//         {            
//            case Seq(line1) => contLine(tail, Nil, line1 - ";")
//            case mem1Lines => multiLine(tail, mem1Lines.init.map(_.ind2).:+(mem1Lines.last.ind2 - ";"))
//         }
//      }
//   }
//   def persistStatements: String = "#" - persistName - persistMems.foldLeft("")(_.nl - _.persistFull.strFold("\n")) 
}