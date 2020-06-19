package br.com.starterpack.resource;

import br.com.starterpack.core.resource.AbstractCrudResource;
import br.com.starterpack.core.resource.ICrudResource;
import br.com.starterpack.entity.Task;
import br.com.starterpack.service.TaskService;
import br.com.starterpack.core.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping(value = "/api/v1/tasks")
public class TaskResource extends AbstractCrudResource<Task, TaskService, String> {

    @Autowired
    private TaskService taskService;

    @Override
    public TaskService getService() {
        return this.taskService;
    }

    @RequestMapping(value = "/toggleDone", method = RequestMethod.PUT)
    public DeferredResult<ResponseEntity<Response>> toggleDone(@RequestBody Task task) {
        final DeferredResult<ResponseEntity<Response>> dr = new DeferredResult<>();
        this.getService().toogleDone(task);
        dr.setResult(ResponseEntity.ok(Response.ok()));
        return dr;
    }
}
