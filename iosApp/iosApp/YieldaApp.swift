import SwiftUI
import Search

@main
struct YieldaApp: App {
    init() {
        InitKoinIosKt.doInitKoinIos()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
