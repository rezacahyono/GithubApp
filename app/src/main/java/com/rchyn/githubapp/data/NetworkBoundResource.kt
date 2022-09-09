package com.rchyn.githubapp.data

import com.rchyn.githubapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    emit(Resource.Loading)
    val flow = try {
        val data = query().first()
        if (shouldFetch(data)) {
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        } else {
            query().map { Resource.Success(it) }
        }
    } catch (throwable: Throwable) {
        query().map { Resource.Error(throwable) }
    }
    emitAll(flow)
}.flowOn(Dispatchers.IO)