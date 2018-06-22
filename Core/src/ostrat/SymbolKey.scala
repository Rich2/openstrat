/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

trait SymbolKey
{
   def sym: Symbol  
}

object SymbolKey
{
   implicit class SymbolKeySeqImp[A <: SymbolKey](thisSeq: Seq[A])
   {
      def symFind(sym: Symbol): Option[A] = thisSeq.find(_.sym == sym)
      def symGet(sym: Symbol): A = symFind(sym).get 
   }
}