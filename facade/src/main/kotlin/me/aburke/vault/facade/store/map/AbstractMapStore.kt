package me.aburke.vault.facade.store.map

abstract class AbstractMapStore<T> {

    protected val entries = mutableMapOf<String, T>()

    protected inline fun updateEntry(id: String, update: (entry: T) -> Unit) {
        val entry = entries[id] ?: throw Error("Entry with ID '${id}' not found in map")
        update(entry)
    }
}
