package com.andevcba.githubmvp.data;

import com.andevcba.githubmvp.data.net.GitHubApiClient;
import com.andevcba.githubmvp.data.repository.InMemoryRepository;
import com.andevcba.githubmvp.data.repository.ReposCache;
import com.andevcba.githubmvp.data.repository.ReposCacheImpl;
import com.andevcba.githubmvp.data.repository.Repository;
import com.andevcba.githubmvp.data.repository.RepositoryFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Provides dependencies.
 *
 * @author lucas.nobile
 */
public class DependencyProvider {

    private static ReposCache reposCache;
    private static Retrofit retrofit;
    private static GitHubApiClient gitHubApiClient;
    private static RepositoryFactory repositoryFactory;
    private static Repository inMemoryRepository;

    public static ReposCache provideReposCache() {
        if (reposCache == null) {
            reposCache = new ReposCacheImpl();
        }
        return reposCache;
    }

    public static Retrofit provideRetrofit() {
        // Add interceptor to log request and response.
        // Use this for testing purpose only.
//            OkHttpClient.Builder builder = new OkHttpClient.Builder();
//            builder.addInterceptor(new LoggingInterceptor());
//            OkHttpClient client = builder.build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(GitHubApiClient.ENDPOINT)
//                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static GitHubApiClient provideGitHubApiClient() {
        if (gitHubApiClient == null) {
            gitHubApiClient = provideRetrofit().create(GitHubApiClient.class);
        }
        return gitHubApiClient;
    }

    public static RepositoryFactory provideRepositoryFactory(ReposCache reposCache) {
        if (repositoryFactory == null) {
            repositoryFactory = new RepositoryFactory(reposCache);
        }
        return repositoryFactory;
    }

    public static Repository provideInMemoryRepository(ReposCache reposCache) {
        if (inMemoryRepository == null) {
            inMemoryRepository = new InMemoryRepository(reposCache);
        }
        return inMemoryRepository;
    }
}