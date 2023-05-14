package ru.netology.service;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.repository.PostRepository;

import java.util.List;
import java.util.Random;

public class PostService {
  private final PostRepository repository;

  public PostService(PostRepository repository) {
    this.repository = repository;
  }

  public List<Post> all() {
    return repository.all();
  }

  public Post getById(long id) {
    return repository.getById(id).orElseThrow(NotFoundException::new);
  }

  public Post save(Post post) {
    if (checkPost(post)) {
      System.out.println("Одинаковые модели Post (id=" +post.getId() + ", content=" + post.getContent() + ") - ошибка - не сохранено");
      return null;
    }
    return repository.save(post);
  }

  public void removeById(long id) {
    System.out.println("id " + id);
    if (checkId(id)) {
      repository.removeById(id);
      System.out.println("Id " + id + " удачно удален");
    } else {
      throw new NotFoundException("Ошибка удаления id -> id " + id + " не существует");
    }
  }

  private boolean checkPost (Post post) {
    for (Post value : repository.all()) {
      if (value.equals(post)) {
        return true;
      } else {
        if (value.getId()!=post.getId() && (value.getContent().equals(post.getContent()))) {
          return true;
        } else if (value.getId()==post.getId() && (!value.getContent().equals(post.getContent()))){
          post.setId(Math.abs(new Random().nextLong()));
          return false;
        }
      }
    }
    return false;
  }

  private boolean checkId (long id) {
    for (Long aLong : repository.getPostRepoMap().keySet()) {
      if (aLong.equals(id)) {
        return true;
      }
    }
    return false;
  }
}

