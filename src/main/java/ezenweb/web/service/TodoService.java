package ezenweb.web.service;

import ezenweb.web.domain.todo.TodoDto;
import ezenweb.web.domain.todo.TodoEntity;
import ezenweb.web.domain.todo.TodoEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TodoService {
    @Autowired
    TodoEntityRepository todoEntityRepository;

    //할일 등록
    @Transactional
    public boolean writeTodo(TodoDto todoDto){
        log.info("service todo post : " +todoDto );
        TodoEntity entity = todoEntityRepository.save(todoDto.toEntity());

        if(entity.getId() > 0){
            return true;
        }

        return false;
    }

    //할일 출력
    @Transactional
    public List<TodoDto> getTodo(){
        List<TodoDto> list = new ArrayList<TodoDto>();;
        List<TodoEntity> entity = todoEntityRepository.findAll();
        System.out.println("service todo get :" + entity);
        entity.forEach((e) -> {
            list.add(e.toDto());
        });

        return list;
    }

    //할일 수정
    @Transactional
    public boolean updateTodo(TodoDto todoDto){
        Optional<TodoEntity> todoEntityOptional = todoEntityRepository.findById(todoDto.getId());

        if(todoEntityOptional.isPresent()){
            TodoEntity entity = todoEntityOptional.get();

            entity.setDone(todoDto.isDone());
            entity.setTitle(todoDto.getTitle());
            return true;
        }
        return false;
    }
    //할일 삭제
    @Transactional
    public boolean deleteTodo(int tno){
        Optional<TodoEntity> optionalTodo = todoEntityRepository.findById(tno);

        if(optionalTodo.isPresent()){
            todoEntityRepository.delete(optionalTodo.get());
            return true;
        }
        return false;
    }
    
}
