/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pParse

case class IndText(str: String) extends Indenter with XCon with CLikeCon with JCon with CssCon
{ 
   override def out(indent: Int) = str   
}