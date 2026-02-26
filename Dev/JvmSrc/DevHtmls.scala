/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*, pWeb.*

trait DevHtmls
{ def jsPathStr(path: DirsAbs, outerModuleName: String, isFast: Boolean, name: String): String
  def scalaVersionStr = "3.8.2"
  def toolStr: String
  def appOuterModuleName: String
  def appNames = StrArr("Diceless", "Discov", "IndRev", "Sors", "WW1", "WW2", "BC305", "Zug", "Dungeon", "Planets", "Chess")
  def egridNames = StrArr("EG1300", "EG1000", "EG640", "EG460", "EG320")

  def main(args: Array[String]): Unit =
  {
    projPathDo { path0 =>
      args.length match
      { case 0 => deb("No args, no files created.")
        case _ if args(0) == "all" => appNames.foreach { name => writeFastFull(path0, appOuterModuleName, name) }
        case _ => args.filter(arg => appNames.exists(_ == arg)).foreach(arg => writeFastFull(path0, "EGridJs", arg))
      }
    }
  }

  def writeFastFull(path: DirsAbs, outerModuleName: String, name: String): Unit =
  { val fastPage: HtmlPage = makeFile(path, outerModuleName, true, name)
    path.subHtmlWrite("Dev/target/DevPages", s"$name${toolStr}Fast", fastPage)
    val fullPage = makeFile(path, outerModuleName, false, name)
    path.subHtmlWrite("Dev/target/DevPages", s"$name${toolStr}Full", fullPage.out)
  }

  def makeFile(path: DirsAbs, outerModuleName: String, isFast: Boolean, name: String): HtmlPage =
  {
    def jsPathStr2 = jsPathStr(path, outerModuleName, isFast, name).enquote

    val noCacheScript = s""" // aid local development in ensuring script not cached during a simple refresh
    |  var script = document.createElement("script");
    |  script.setAttribute("type", "text/javascript");
    |  script.setAttribute("src", ${jsPathStr2}+Date.now().toString());
    |  document.getElementsByTagName("head")[0].appendChild(script);
    |  script.addEventListener('load', function(e) { ${name}AppJs.main(); });""".stripMargin

    val style = HtmlStyle(CssBody(MarginDec(0.px), DecOverflowHidden))
    val head = HtmlHead.title("OpenStrat:" -- name, HtmlNoCache, style)
    val script = HtmlScript.inlineJsStr(noCacheScript)
    val body = HtmlBody(HtmlCanvas.id("scanv"), HtmlNoScript(), script)
    HtmlPage(head, body)
  }
}

object DevSbtHtmls extends DevHtmls
{ override val toolStr: String = "Sbt"
  override val appOuterModuleName: String = "AppsJs"

  override def jsPathStr(path: DirsAbs, outerModuleName: String, isFast: Boolean, name: String): String =
  { val jsStr = ife(isFast, "fast", "")
    "../../../Apps/AppsJs/target/scala-$scalaVersionStr/appsjs-${name}-${jsStr}opt/main.js?"
  }
}

object DevMillHtmls extends DevHtmls
{ override val toolStr: String = "Mill"
  override val appOuterModuleName: String = "AppJs"
  override def jsPathStr(path: DirsAbs, outerModuleName: String, isFast: Boolean, name: String): String =
  { val jsStr = ife(isFast, "fast", "full")
    s"../../../out/$outerModuleName/$name/${jsStr}Opt.dest/out.js?"
  }
}