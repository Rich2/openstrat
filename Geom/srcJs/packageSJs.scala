/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import scalajs.js.Any.fromFunction1, org.scalajs.dom._

package object pSJs
{
  implicit class ImpJsDynamic(a: Any)
  { def dyn: scalajs.js.Dynamic = a.asInstanceOf[scalajs.js.Dynamic]      
  }
   
  def createSpan: html.Span = document.createElement("span").asInstanceOf[html.Span]
   
  def createButton(str: String, e: MouseEvent => Unit): html.Button =
  { val but = document.createElement("button").asInstanceOf[html.Button]
    but.appendChild(document.createTextNode(str))
    but.onclick = e
    but
  }
   
  def createStyle(cssString: String): html.Style =
  { val style: html.Style = document.createElement("style").asInstanceOf[html.Style]
    style.appendChild(document.createTextNode(cssString))
    style
  }
  
  def createInternalStyle(cssString: String): html.Style =
  { val style: html.Style = document.createElement("style").asInstanceOf[html.Style]
    style.appendChild(document.createTextNode(cssString))
    document.head.appendChild(style)
    style
  }
  
  implicit class ImpHtmlNode(thisNode: Node)
  {
     def addText(str: String): raw.Text = {
        val t = document.createTextNode(str)
        thisNode.appendChild(t)
        t
     }
  } 
}