// build.sc
import mill._, scalalib._, scalajslib._

trait Common extends ScalaModule
{ def scalaVersion = "2.12.7"
  def scalacOptions = Seq("-feature", "-language:implicitConversions", "-deprecation", "-target:jvm-1.8", "-encoding", "UTF-8", "-unchecked", "-Xfuture", "-Xlint")
}

object Macros extends Common
{ def ivyDeps = Agg(ivy"${scalaOrganization()}:scala-reflect:${scalaVersion()}")
}

object Core extends Common
{ def moduleDeps = Seq(Macros)
  object test extends Tests
  {
    def ivyDeps = Agg(ivy"com.lihaoyi::utest:0.6.6")
    def testFrameworks = Seq("utest.runner.Framework")    
  }  
}

object FxStrat extends Common
{ def moduleDeps = Seq(Core)
  def mainClass = Some("ostrat.pFx.DevApp") 
  import mill.modules.Assembly._
  def scalaLibraryIvyDeps = T { Agg.empty }   
}

object JsStrat extends ScalaJSModule with Common
{ def ivyDeps = Agg(ivy"org.scala-js::scalajs-dom_sjs0.6:0.9.6")
  def moduleDeps = Seq(Core)
  def sources = T.sources( millSourcePath / 'src, millSourcePath / 'srcPlay)
  def artifactName = "play"
  def scalaJSVersion = "0.6.25"
}
