/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package object pParse
{ 
   implicit def indentConLikeString (s: String): IndText = IndText(s)  
}