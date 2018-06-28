/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** This package for the manipulation of Html, XML CSS and Javascript. Some of the functionality could be replaced by Scalatags. */
package object pWeb
{ 
   implicit def indentConLikeString(s: String): IndText = IndText(s)  
}